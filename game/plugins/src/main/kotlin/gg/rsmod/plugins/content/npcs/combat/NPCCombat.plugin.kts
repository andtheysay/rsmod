package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.plugins.content.combat.strategy.*
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.game.model.attr.*
import gg.rsmod.plugins.content.mechanics.poison.*
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.combat.canEngageCombat
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.removeCombatTarget
import gg.rsmod.plugins.content.npcs.combat.configuration.CombatConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.AttackMethod
import gg.rsmod.plugins.content.npcs.combat.formula.NPCCombatFormula

on_command("combat") {
    player.getSkills().setBaseLevel(Skills.HITPOINTS, 99)
    player.getSkills().setBaseLevel(Skills.PRAYER, 99)
    world.spawn(Npc(239, player.tile, world))
}

on_world_init {
    CombatConfiguration.loadAll().forEach { configuration ->

        // Setting base configuration for npc combat
        configuration.ids.forEach { id ->
            set_combat_def(id) {

                this.stats {
                    this.hitpoints = configuration.hitpoints
                    this.attack = configuration.attack
                    this.defence = configuration.defence
                    this.strength = configuration.strength
                    this.magic = configuration.magic
                    this.ranged = configuration.ranged
                }

                this.aggro {
                    this.radius = configuration.aggroRadius
                    this.searchDelay = configuration.aggroSearchDelay
                    this.aggroTimer = configuration.aggroTimer
                }

                this.anims {
                    this.block = configuration.blockAnim
                    this.death = configuration.deathAnim
                }

                this.species {
                    configuration.species.forEach(this::of)
                }

                this.configs {
                    this.respawnDelay = configuration.respawnDelay
                    this.attackSpeed = configuration.attackSpeed
                }
            }
        }
        val first = configuration.ids.first()
        val others = configuration.ids.drop(1).toIntArray()

        /// TODO apparently the npc combat does not take the care
        /// of holding and keeping the target npc,
        /// everytime the player attacks npc no matter what the focus target attribute
        /// is settled to the latest one, meaning that many players will start the npc
        /// combat at once, i need to move this to more fine-grained combat logic that persists
        /// the target(s) so the npc can cycle between them or just attack one refusing other
        /// players to attack it.
        on_npc_combat(first, *others) {
            this.npc.queue {
                combat(this, configuration)
            }
        }
    }
}

suspend fun combat(task: QueueTask, configuration: CombatConfiguration) {
    val npc = task.npc
    var target = npc.getCombatTarget() as Player? ?: return

    while (npc.canEngageCombat(target)) {
        npc.facePawn(target)
        /// pick a random method, TODO weighted bag
        val method = configuration.attackMethods.random()

        /// first, ensure we are close enough to the target
        if (!this.ensureDistance(task, npc, target, method)) {
            continue
        }

        /// start animating the npc when performing an attack
        if (method.attackerGraphic != 0) {
            npc.graphic(method.attackerGraphic)
        }

        if (method.attackAnim != 0) {
            npc.animate(method.attackAnim)
        }

        /// The damage delay is calculated based on the ase damage delay config
        /// and depending whether the projectile is settled for this method
        /// a damage delay is a sum of a base delay and projectile life span
        /// which is a sum of projectile delay and time it is required for the projectile
        /// to arrive at target tile.
        val damageDelay = when {
            method.hasProjectile() -> {
                // when method has a projectile config, we shoot it at the target
                this.shootProjectile(npc, target, method)
                val frontTargetTile = npc.getFrontFacingTile(target)
                val targetCentreTile = target.getCentreTile()
                val projectileHitDelay = RangedCombatStrategy.getHitDelay(frontTargetTile, targetCentreTile)
                method.damageDelay + projectileHitDelay
            }
            else -> method.damageDelay
        }

        /// deal the damage to the target
        this.dealDamage(npc, target, method, damageDelay)

        /// waiting for the next attack
        if (method.nextAttackDelay > 0) {
            task.wait(method.nextAttackDelay)
        }

        /// ensuring the target is still available
        target = npc.getCombatTarget() as Player? ?: break
    }

    npc.resetFacePawn()
    npc.removeCombatTarget()
}

fun dealDamage(attacker: Npc, target: Player, method: AttackMethod, delay: Int) {
    val formula = NPCCombatFormula(
            method.startingAttackStyle,
            method.endingAttackStyle,
            method.minHit,
            method.maxHit
    )

    attacker.dealHit(target, formula, delay) { hit ->
        // when hit lands, check if we should poison the target
        if (hit.landed) {
            this.tryPoisonTarget(target, method.poisonChance, method.poisonDamage)
        }

        if (method.targetGraphic != 0) {
            target.graphic(method.targetGraphic)
        }
    }
}

fun tryPoisonTarget(target: Player, chance: Double, damage: Int) {
    if (chance == 0.0) {
        return
    }

    val alreadyPoisoned = (target.attr[POISON_TICKS_LEFT_ATTR] ?: 0) != 0
    if (alreadyPoisoned) {
        return
    }

    if (world.random(100) >= chance) {
        target.poison(damage) {
            target.message("You have been poisoned.")
        }
    }
}

suspend fun ensureDistance(task: QueueTask, attacker: Npc, target: Player, method: AttackMethod): Boolean {
    val attackDistance = method.attackDistance
    val withinReach = attacker.tile.getDistance(target.tile) <= attackDistance

    if (withinReach) {
        return true
    }

    /// TODO when config specifies minimum distance to attack
    /// and is also specifying that it should not move to an attack
    /// what should happen in this scenario? should it just assume that
    /// attack is ok to perform?
    if (!method.moveToAttack) {
        return true
    }

    attacker.walkTo(task, target.tile)
    return attacker.tile.getDistance(target.tile) <= attackDistance

}

fun shootProjectile(attacker: Npc, target: Player, method: AttackMethod): Projectile {
    val projectile = attacker.createProjectile(
            target,
            method.projectile,
            method.projectileStartHeight,
            method.projectileEndHeight,
            method.projectileDelay,
            15
    )
    world.spawn(projectile)
    return projectile
}
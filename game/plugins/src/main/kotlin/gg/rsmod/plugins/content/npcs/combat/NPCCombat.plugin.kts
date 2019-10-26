package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.plugins.content.mechanics.poison.*
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.combat.canEngageCombat
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.removeCombatTarget
import gg.rsmod.plugins.content.npcs.combat.configuration.CombatConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.AttackMethod
import gg.rsmod.plugins.content.npcs.combat.formula.NPCCombatFormula

on_command("combat") {
    world.spawn(Npc(2, player.tile, world))
}

on_world_init {
    CombatConfiguration.loadAll().forEach { configuration ->
        val first = configuration.ids.first()
        val others = configuration.ids.drop(1).toIntArray()

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

        on_npc_combat(first, *others) {
            npc.queue {
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

        // pick a random method, TODO weighted bag
        val method = configuration.attackMethods.random()

        // first, ensure we are close enough to the target
        if (!this.ensureDistance(task, npc, target, method)) {
            continue;
        }

        /// start animating the npc when performing an attack
        npc.graphic(method.attackerGraphic)
        npc.animate(method.attackAnim)

        // when method has a projectile config, we shoot it at the target
        if (method.hasProjectile()) {
            this.shootProjectile(npc, target, method)
        }

        // deal the damage to the target
        this.dealDamage(npc, target, method)

        /// waiting for the next attack
        task.wait(method.nextAttackDelay)

        // ensuring the target is still available
        target = npc.getCombatTarget() as Player? ?: break
    }

    npc.resetFacePawn()
    npc.removeCombatTarget()
}

fun dealDamage(attacker: Npc, target: Player, method: AttackMethod) {
    val formula = NPCCombatFormula(
            method.startingAttackStyle,
            method.endingAttackStyle,
            method.minHit,
            method.maxHit
    )

    attacker.dealHit(target, formula = formula, delay = method.damageDelay) { hit ->
        // when hit lands, check if we should poison the target
        if (hit.landed) {
            this.tryPoisonTarget(target, method.poisonChance)
        }
        target.graphic(method.targetGraphic)
    }
}

fun tryPoisonTarget(target: Player, chance: Double) {
    if (world.random(100) >= chance) {
        target.poison(8) {
            target.forceChat("You have been poisoned.")
        }
    }
}

suspend fun ensureDistance(task: QueueTask, attacker: Npc, target: Player, method: AttackMethod): Boolean {
    val attackDistance = method.attackDistance
    val withinReach = attacker.tile.getDistance(target.tile) > attackDistance

    if (!method.moveToAttack) {
        return withinReach
    }

    if (!withinReach) {
        attacker.walkTo(task, target.tile)
    }

    return attacker.tile.getDistance(target.tile) > attackDistance
}

fun shootProjectile(attacker: Npc, target: Player, method: AttackMethod) {
    val projectile = attacker.createProjectile(
            target,
            method.projectile,
            method.projectileStartHeight,
            method.projectileEndHeight,
            method.projectileDelay,
            15
    )
    world.spawn(projectile)

}
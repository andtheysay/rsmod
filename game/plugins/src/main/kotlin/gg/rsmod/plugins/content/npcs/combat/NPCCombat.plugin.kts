package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.combat.canEngageCombat
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.removeCombatTarget
import gg.rsmod.plugins.content.npcs.combat.configuration.CombatConfiguration

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
    var target = npc.getCombatTarget() ?: return

    while (npc.canEngageCombat(target)) {
        npc.facePawn(target)



        npc.dealHit(target, formula = MeleeCombatFormula, delay = 1) {

        }

        task.wait(2)
        target = npc.getCombatTarget() ?: break
    }

    npc.resetFacePawn()
    npc.removeCombatTarget()
}
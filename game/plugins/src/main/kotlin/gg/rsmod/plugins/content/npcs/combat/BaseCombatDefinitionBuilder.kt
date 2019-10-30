package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.game.model.combat.NpcCombatDef
import gg.rsmod.plugins.api.dsl.NpcCombatDsl
import gg.rsmod.plugins.content.npcs.combat.configuration.NPCCombatConfiguration

class BaseCombatDefinitionBuilder(val configuration: NPCCombatConfiguration) {

    fun build(): NpcCombatDef {
        val builder = NpcCombatDsl.Builder()
        builder.apply {
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
        return builder.build()
    }

}
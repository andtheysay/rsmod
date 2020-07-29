package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.game.model.combat.NpcCombatDef
import gg.rsmod.plugins.api.dsl.NpcCombatDsl
import gg.rsmod.plugins.content.npcs.combat.configuration.NPCCombatConfiguration

class BaseCombatDefinitionBuilder(val configuration: NPCCombatConfiguration) {

    fun build(): NpcCombatDef {
        val builder = NpcCombatDsl.Builder()
        builder.apply {
            this.stats {
                val stats = configuration.stats
                this.hitpoints = stats.hitpoints
                this.attack = stats.attack
                this.defence = stats.defence
                this.strength = stats.strength
                this.magic = stats.magic
                this.ranged = stats.ranged
            }

            this.aggro {
                val aggression = configuration.aggression
                this.radius = aggression.radius
                this.searchDelay = aggression.searchDelay
                this.aggroTimer = aggression.timer
            }

            this.anims {
                this.block = configuration.block.animation
                this.death = configuration.death.animation
            }

            this.species {
                configuration.species.forEach(this::of)
            }

            this.configs {
                this.respawnDelay = configuration.respawn.delay
                this.attackSpeed = configuration.attackSpeed
            }
        }
        return builder.build()
    }

}
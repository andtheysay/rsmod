package gg.rsmod.plugins.content.npcs.combat.core.attack.hit

import gg.rsmod.game.model.World
import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.mechanics.poison.poison
import gg.rsmod.plugins.content.npcs.combat.configuration.NPCAttackConfiguration
import gg.rsmod.plugins.content.npcs.combat.formula.NPCCombatFormula

class NPCHit(
        private val world: World,
        private val npc: Npc,
        private val target: Player,
        private val configuration: NPCAttackConfiguration,
        private val delay: Int
) {

    suspend fun execute() {
        val formula = NPCCombatFormula(
                configuration.startingAttackStyle,
                configuration.endingAttackStyle,
                configuration.minHit,
                configuration.maxHit
        )

        npc.dealHit(target, formula, delay) { hit ->
            // when hit lands, check if we should poison the target
            if (hit.landed) {
                this.tryPoisonTarget(target, configuration.poisonChance, configuration.poisonDamage)
            }

            if (configuration.targetGraphic != 0) {
                target.graphic(configuration.targetGraphic)
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
}


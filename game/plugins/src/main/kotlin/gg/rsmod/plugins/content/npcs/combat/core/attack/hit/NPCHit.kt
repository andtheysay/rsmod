package gg.rsmod.plugins.content.npcs.combat.core.attack.hit

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.combat.dealHit
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.NPCTargetHitConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectPredicate
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffectFactory
import gg.rsmod.plugins.content.npcs.combat.formula.NPCCombatFormula

class NPCHit(
        private val world: World,
        private val npc: Npc,
        private val target: Pawn,
        private val configuration: NPCTargetHitConfiguration,
        private val delay: Int
) {

    suspend fun execute() {
        val formula = NPCCombatFormula(
                configuration.style.start,
                configuration.style.end,
                configuration.min,
                configuration.max
        )

        val accuracy = formula.getAccuracy(this.npc, this.target)
        val maxHit = formula.getMaxHit(this.npc, this.target)
        val landHit = accuracy >= world.randomDouble()

        val effects = this.configuration.effects ?: emptyList()
        if (landHit) {
            npc.dealHit(target, maxHit, landHit, this.delay) {
                effects
                        .filter { it.predicate == HitEffectPredicate.Landed }
                        .map(NPCHitEffectFactory::create)
                        .forEach { it.apply(this.target) }
            }
        } else {
            effects
                    .filter { it.predicate == HitEffectPredicate.Blocked }
                    .map(NPCHitEffectFactory::create)
                    .forEach { it.apply(this.target) }
        }

    }
}


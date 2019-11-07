package gg.rsmod.plugins.content.npcs.combat.core.attack.hit

import gg.rsmod.game.model.World
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
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
    val formula = NPCCombatFormula(
            this.configuration.style.start,
            this.configuration.style.end,
            this.configuration.min,
            this.configuration.max
    )

    suspend fun execute() {
        val hitLanded = this.dealHit()
        this.applyEffects(hitLanded)
    }

    /**
     * Deals hit to the target
     *
     * @return whether the hit landed
     */
    private fun dealHit(): Boolean {
        val accuracy = formula.getAccuracy(this.npc, this.target)
        val maxHit = formula.getMaxHit(this.npc, this.target)
        val hitLanded = accuracy >= world.randomDouble()

        val shouldDealHit = configuration.style.end == CombatClass.MELEE || hitLanded
        if (shouldDealHit) {
            npc.dealHit(target, maxHit, hitLanded, this.delay)
        }
        return hitLanded
    }

    /**
     * Applies the hit effects
     */
    private fun applyEffects(hitLanded: Boolean) {
        val effectConfigurations = this.configuration.effects ?: emptyList()
        val hitEffects = when (hitLanded) {
            true -> effectConfigurations
                    .filter { it.predicate == HitEffectPredicate.Landed }
                    .map(NPCHitEffectFactory::create)
            false -> effectConfigurations
                    .filter { it.predicate == HitEffectPredicate.Blocked }
                    .map(NPCHitEffectFactory::create)
        }
        val eitherHitEffects = effectConfigurations
                .filter { it.predicate == HitEffectPredicate.Either }
                .map(NPCHitEffectFactory::create)

        val effects = hitEffects + eitherHitEffects
        effects.forEach { it.apply(this.target) }
    }
}


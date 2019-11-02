package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect

import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectKind
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.AnimateEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.GraphicEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.PoisonEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.venom.VenomEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.NPCHit
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic.AnimateEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic.GraphicEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.posion.PoisonEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.venom.VenomEffect
import java.lang.IllegalStateException

object NPCHitEffectFactory {

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    fun create(cfg: HitEffectConfiguration): NPCHitEffect {
        return when (cfg.kind) {
            HitEffectKind.Poison -> PoisonEffect(cfg.configuration as PoisonEffectConfiguration)
            HitEffectKind.Venom -> VenomEffect(cfg.configuration as VenomEffectConfiguration)
            HitEffectKind.Graphic -> GraphicEffect(cfg.configuration as GraphicEffectConfiguration)
            HitEffectKind.Animate -> AnimateEffect(cfg.configuration as AnimateEffectConfiguration)
            else -> {
                throw IllegalStateException("Hit effect behavior for kind ${cfg.kind} not implemented")
            }
        }
    }
}
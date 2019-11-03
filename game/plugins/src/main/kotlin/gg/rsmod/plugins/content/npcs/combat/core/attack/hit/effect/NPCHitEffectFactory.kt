package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect

import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectKind
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.AnimateEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.GraphicEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.freeze.FreezeEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.PoisonEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.stat.AlterStatEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.venom.VenomEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.NPCHit
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic.AnimateEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic.GraphicEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.freeze.FreezeEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.posion.PoisonEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.stat.AlterStatEffect
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.venom.VenomEffect
import java.lang.IllegalStateException

object NPCHitEffectFactory {

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    fun create(cfg: HitEffectConfiguration): NPCHitEffect {
        //disease
        //venom damage
        //stat altering
        //stun
        //message
        //sound
        return when (cfg.kind) {
            HitEffectKind.Poison -> PoisonEffect(cfg.configuration as PoisonEffectConfiguration)
            HitEffectKind.Venom -> VenomEffect(cfg.configuration as VenomEffectConfiguration)
            HitEffectKind.Freeze -> FreezeEffect(cfg.configuration as FreezeEffectConfiguration)
            HitEffectKind.AlterStat -> AlterStatEffect(cfg.configuration as AlterStatEffectConfiguration)
            HitEffectKind.Graphic -> GraphicEffect(cfg.configuration as GraphicEffectConfiguration)
            HitEffectKind.Animate -> AnimateEffect(cfg.configuration as AnimateEffectConfiguration)
            else -> {
                throw IllegalStateException("Hit effect behavior for kind ${cfg.kind} not implemented")
            }
        }
    }
}
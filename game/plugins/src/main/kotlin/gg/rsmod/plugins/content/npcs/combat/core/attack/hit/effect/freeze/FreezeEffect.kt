package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.freeze

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.plugins.api.ext.freeze
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.freeze.FreezeEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class FreezeEffect(private val cfg: FreezeEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        target.freeze(this.cfg.duration)
    }
}
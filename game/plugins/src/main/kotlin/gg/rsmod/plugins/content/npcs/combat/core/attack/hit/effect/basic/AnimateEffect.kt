package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.AnimateEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class AnimateEffect(private val cfg: AnimateEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Player) {
        target.animate(cfg.animation)
    }

}
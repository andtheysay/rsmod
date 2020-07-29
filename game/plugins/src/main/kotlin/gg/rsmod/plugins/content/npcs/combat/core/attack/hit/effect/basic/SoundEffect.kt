package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.SoundEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class SoundEffect(private val cfg: SoundEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        (target as? Player)?.let {
            it.playSound(cfg.id, cfg.volume, cfg.delay)
        }
    }

}
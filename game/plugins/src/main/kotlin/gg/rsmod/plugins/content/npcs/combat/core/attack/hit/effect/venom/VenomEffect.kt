package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.venom

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.plugins.content.mechanics.effect.venom.Venom
import gg.rsmod.plugins.content.mechanics.effect.venom.applyVenom
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.venom.VenomEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class VenomEffect(private val cfg: VenomEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        if (target.world.random(100) <= this.cfg.chance) {
            target.applyVenom()
        }
    }
}
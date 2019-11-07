package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.posion

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.mechanics.effect.poison.applyPoison
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.VenomEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class VenomEffect(private val cfg: VenomEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        if (target.world.random(100) <= this.cfg.chance) {
            target.applyPoison(this.cfg.damage) {
                (target as? Player)?.let {
                    target.message("You have been venomed")
                }
            }
        }
    }
}
package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.posion

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.content.mechanics.effect.poison.poison
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.PoisonEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class PoisonEffect(private val cfg: PoisonEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        (target as? Player)?.let {
            if (target.world.random(100) <= this.cfg.chance) {
                target.poison(this.cfg.damage) {
                    target.message("You have been poisoned")
                }
            }
        }

    }
}
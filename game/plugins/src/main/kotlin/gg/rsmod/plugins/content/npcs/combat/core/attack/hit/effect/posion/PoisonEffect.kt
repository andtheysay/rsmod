package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.posion

import gg.rsmod.game.model.Hit
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.PoisonEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class PoisonEffect(private val cfg: PoisonEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Player) {
        println("Poisoning")
    }
}
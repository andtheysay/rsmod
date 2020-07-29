package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.basic

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.GraphicEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class GraphicEffect(val cfg: GraphicEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        target.graphic(cfg.graphic)
    }
}
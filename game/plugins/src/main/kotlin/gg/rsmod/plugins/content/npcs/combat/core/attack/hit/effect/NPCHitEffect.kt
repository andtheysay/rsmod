package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect

import gg.rsmod.game.model.entity.Pawn

interface NPCHitEffect {
    fun apply(target: Pawn)
}
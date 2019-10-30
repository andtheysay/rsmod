package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect

import gg.rsmod.game.model.Hit
import gg.rsmod.game.model.entity.Player

interface NPCHitEffect {
    fun apply(target: Player)
}
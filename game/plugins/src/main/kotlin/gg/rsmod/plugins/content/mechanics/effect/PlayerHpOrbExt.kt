package gg.rsmod.plugins.content.mechanics.effect

import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.ext.setVarp

private const val VARP = 102

enum class OrbState {
    NONE,
    POISON,
    VENOM
}

fun Player.setHpOrb(state: OrbState) {
    val value = when (state) {
        OrbState.NONE -> 0
        OrbState.POISON -> 1
        OrbState.VENOM -> 1_000_000
    }
    this.setVarp(VARP, value)
}

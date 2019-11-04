package gg.rsmod.plugins.content.mechanics.effect.venom

import gg.rsmod.game.model.entity.Pawn

fun Pawn.applyVenom() {
    if (Venom.isAppliedTo(this)) {
        return
    }
    Venom.apply(this)
}
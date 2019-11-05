package gg.rsmod.plugins.content.mechanics.effect.poison

import gg.rsmod.game.model.entity.Pawn

/**
 * @author Tom <rspsmods@gmail.com>
 */

fun Pawn.applyPoison(initialDamage: Int, onPoison: () -> Unit) {
    if (Poison.applyPoison(this, initialDamage, PoisonKind.Regular)) {
        onPoison();
    }
}

fun Pawn.applyVenom(initialDamage: Int, onVenom: () -> Unit) {
    if (Poison.applyPoison(this, initialDamage, PoisonKind.Venom)) {
        onVenom();
    }
}

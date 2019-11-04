package gg.rsmod.plugins.content.mechanics.effect.poison

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.mechanics.effect.OrbState
import gg.rsmod.plugins.content.mechanics.effect.setHpOrb

/**
 * @author Tom <rspsmods@gmail.com>
 */

fun Pawn.poison(initialDamage: Int, onPoison: () -> Unit) {
    if (!Poison.isImmune(this) && Poison.poison(this, initialDamage)) {
        if (this is Player) {
            this.setHpOrb(OrbState.POISON)
        }
        onPoison()
    }
}
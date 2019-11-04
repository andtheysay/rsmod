package gg.rsmod.plugins.content.mechanics.effect.venom

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.content.mechanics.effect.*
import java.lang.Integer.min

object Venom {
    const val INITIAL_DAMAGE = 6
    const val MAX_DAMAGE = 20
    val TIMER = TimerKey()
    val TICKS = AttributeKey<Int>(persistenceKey = "venom_ticks", resetOnDeath = true)
    const val VENOM_TICK_DELAY = 18

    /**
     * Calculates the venom damage based on elapsed time since the venom has been applied
     */
    fun calculateDamage(pawn: Pawn): Int {
        if (this.isAppliedTo(pawn)) {
            return 0
        }
        return min(INITIAL_DAMAGE + getVenomTicks(pawn) * 2, MAX_DAMAGE)
    }

    /**
     * Checks whether the pawn has a venom
     */
    fun isAppliedTo(pawn: Pawn) = pawn.timers.has(TIMER)

    /**
     * Get the amount of ticks the pawn has had venom for
     */
    fun getVenomTicks(pawn: Pawn): Int {
        return pawn.attr[TICKS] ?: 0
    }

    /**
     * Applies the venom to the pawn
     */
    fun apply(pawn: Pawn) {
        pawn.timers[TIMER] = VENOM_TICK_DELAY
        pawn.attr[TICKS] = 0
        (pawn as? Player)?.setHpOrb(OrbState.VENOM)
    }

    /**
     * Removes the venom from pawn
     */
    fun remove(pawn: Pawn) {
        pawn.timers.remove(TIMER)
        pawn.attr.remove(TICKS)
        (pawn as? Player)?.setHpOrb(OrbState.NONE)
    }
}
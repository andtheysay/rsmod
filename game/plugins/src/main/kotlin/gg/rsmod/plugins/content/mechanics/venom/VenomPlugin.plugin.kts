package gg.rsmod.plugins.content.mechanics.venom

import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.content.mechanics.poison.Poison

private val VENOM_TIMER_KEY = TimerKey()
private val VENOM_TICKS_LEFT_ATTR = AttributeKey<Int>(persistenceKey = "venom_ticks_left", resetOnDeath = true)
private val VENOM_TICK_DELAY = 18

on_player_death {
    this.player.timers.remove(VENOM_TIMER_KEY)
    Poison.setHpOrb(player, Poison.OrbState.NONE)
}


on_timer(VENOM_TIMER_KEY) {
    val ticksLeft = pawn.attr[VENOM_TICKS_LEFT_ATTR] ?: 0
    val pawn = pawn

    when {
        ticksLeft == 0 && pawn is Player -> Poison.setHpOrb(pawn, Poison.OrbState.NONE)
        ticksLeft < 0 -> pawn.attr[VENOM_TICKS_LEFT_ATTR] = ticksLeft + 1
        else -> {
            pawn.attr[VENOM_TICKS_LEFT_ATTR] = ticksLeft - 1
            pawn.hit(damage = Poison.getDamageForTicks(ticksLeft), type = HitType.POISON)
        }
    }
    pawn.timers[VENOM_TIMER_KEY] = VENOM_TICK_DELAY
}
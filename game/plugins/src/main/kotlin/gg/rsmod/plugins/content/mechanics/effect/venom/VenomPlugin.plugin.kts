package gg.rsmod.plugins.content.mechanics.effect.venom

import gg.rsmod.plugins.content.mechanics.effect.OrbState
import gg.rsmod.plugins.content.mechanics.effect.setHpOrb


on_player_death {
    Venom.remove(player)
    player.setHpOrb(OrbState.NONE)
}


on_timer(Venom.TIMER) {
    val pawn = pawn

    pawn.hit(damage = Venom.calculateDamage(pawn), type = HitType.VENOM)
    pawn.attr[Venom.TICKS] = Venom.getVenomTicks(pawn) + 1
    pawn.timers[Venom.TIMER] = Venom.VENOM_TICK_DELAY
}
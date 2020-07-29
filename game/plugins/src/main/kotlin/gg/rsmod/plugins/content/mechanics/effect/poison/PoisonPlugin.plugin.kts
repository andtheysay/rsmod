package gg.rsmod.plugins.content.mechanics.effect.poison

on_command("venom_me") {
    player.getSkills().alterCurrentLevel(Skills.HITPOINTS, 255, 255);
    Poison.applyPoison(player, 6, PoisonKind.Venom)
}

on_command("poison_me") {
    player.getSkills().alterCurrentLevel(Skills.HITPOINTS, 255, 255);
    Poison.applyPoison(player, 6, PoisonKind.Regular)
}

on_player_death {
    Poison.removeFrom(player)
}

on_timer(Poison.HIT_TIMER) {
    Poison.applyNextHitFor(pawn)
}
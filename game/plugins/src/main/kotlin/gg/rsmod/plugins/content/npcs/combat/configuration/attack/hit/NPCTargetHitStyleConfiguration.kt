package gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit

import gg.rsmod.game.model.combat.CombatClass

data class NPCTargetHitStyleConfiguration(
        val start: CombatClass,
        val end: CombatClass
)
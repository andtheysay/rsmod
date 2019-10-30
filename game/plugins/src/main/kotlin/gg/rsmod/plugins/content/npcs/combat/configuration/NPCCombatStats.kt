package gg.rsmod.plugins.content.npcs.combat.configuration

data class NPCCombatStats(
        val combat: Int,
        val hitpoints: Int,
        val attack: Int,
        val strength: Int,
        val defence: Int,
        val magic: Int,
        val ranged: Int
)
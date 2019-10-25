package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.plugins.content.npcs.combat.configuration.CombatConfiguration

fun main() {
    val configs = CombatConfiguration.loadAll()
    println("configs = ${configs}")
}
package gg.rsmod.plugins.content.npcs.combat

import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.npcs.combat.configuration.NPCCombatConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.*

on_command("combat") {
    player.getSkills().setBaseLevel(Skills.HITPOINTS, 99)
    player.getSkills().setBaseLevel(Skills.PRAYER, 99)
    world.spawn(Npc(239, player.tile, world))
}

on_command("venom_test") {
    player.getSkills().setBaseLevel(Skills.HITPOINTS, 99)
    player.getSkills().setBaseLevel(Skills.PRAYER, 99)
    world.spawn(Npc(1119, player.tile, world))
}
on_world_init {
    NPCCombatConfiguration.loadAll().forEach { configuration ->
        setupBaseDefinition(configuration)
        setupCombatLogic(configuration)
    }
}

fun setupBaseDefinition(configuration: NPCCombatConfiguration) {
    val baseDefinition = BaseCombatDefinitionBuilder(configuration).build()
    configuration.ids.forEach { id -> set_combat_def(id, baseDefinition) }
}

fun setupCombatLogic(configuration: NPCCombatConfiguration) {
    val first = configuration.ids.first()
    val others = configuration.ids.drop(1).toIntArray()

    on_npc_combat(first, *others) {
        this.npc.queue {
            val combat = NPCCombatFactory.create(this, world, this.npc, configuration)
            combat.execute()
        }
    }
}
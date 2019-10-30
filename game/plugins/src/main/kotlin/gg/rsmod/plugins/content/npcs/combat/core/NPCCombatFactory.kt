package gg.rsmod.plugins.content.npcs.combat.core

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.npcs.combat.configuration.NPCCombatConfiguration

object NPCCombatFactory {
    fun create(task: QueueTask, world: World, npc: Npc, configuration: NPCCombatConfiguration): NPCCombat {
        return NPCCombat(task, world, npc, configuration.attackMethods)
    }
}
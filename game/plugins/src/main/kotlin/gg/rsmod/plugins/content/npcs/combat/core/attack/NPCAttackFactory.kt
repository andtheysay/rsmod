package gg.rsmod.plugins.content.npcs.combat.core.attack

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.NPCAttackConfiguration

object NPCAttackFactory {
    fun create(task: QueueTask, world: World, npc: Npc, target: Pawn, cfg: NPCAttackConfiguration): NPCAttack {
        return NPCAttack(task, world, npc, target, cfg)
    }
}
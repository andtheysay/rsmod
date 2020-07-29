package gg.rsmod.plugins.content.npcs.combat.core.attack.hit

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.NPCAttackConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.NPCTargetHitConfiguration

object NPCHitFactory {

    fun create(world: World, npc: Npc, target: Pawn, cfg: NPCTargetHitConfiguration, delay: Int): NPCHit {
        return NPCHit(world, npc, target, cfg, delay)
    }
}
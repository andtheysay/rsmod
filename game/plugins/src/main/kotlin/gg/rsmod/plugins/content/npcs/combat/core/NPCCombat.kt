package gg.rsmod.plugins.content.npcs.combat.core

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.content.combat.canEngageCombat
import gg.rsmod.plugins.content.combat.getCombatTarget
import gg.rsmod.plugins.content.combat.removeCombatTarget
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.NPCAttackConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.NPCAttackFactory
import gg.rsmod.plugins.content.npcs.combat.core.attack.NPCAttack
import org.apache.logging.log4j.LogManager

class NPCCombat(
        private val task: QueueTask,
        private val world: World,
        private val npc: Npc,
        private val attackConfigurations: List<NPCAttackConfiguration>
) {


    suspend fun execute() {
        var target = this.getTarget() ?: return
        while (this.npc.canEngageCombat(target)) {
            this.npc.facePawn(target)

            val attack = this.prepareNextAttack(target)
            if (!attack.execute()) {
                this.task.wait(1)
            }
            // Switching between targets
            target = this.getTarget() ?: break
        }
        this.npc.resetFacePawn()
        this.npc.removeCombatTarget()
    }

    private fun prepareNextAttack(target: Player): NPCAttack {
        LOG.debug("${this.npc} is preparing attack towards ${target}")
        val nextAttackConfiguration = this.attackConfigurations.random()
        return NPCAttackFactory.create(this.task, this.world, this.npc, target, nextAttackConfiguration)
    }

    private fun getTarget(): Player? {
        return this.npc.getCombatTarget() as Player?
    }

    companion object {
        private val LOG = LogManager.getLogger()
    }
}
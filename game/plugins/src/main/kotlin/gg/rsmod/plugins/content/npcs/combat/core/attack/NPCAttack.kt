package gg.rsmod.plugins.content.npcs.combat.core.attack

import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.ext.createProjectile
import gg.rsmod.plugins.content.combat.moveToAttackRange
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.NPCAttackConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.projectile.NPCAttackProjectileConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.NPCHit
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.NPCHitFactory
import org.apache.logging.log4j.LogManager

class NPCAttack(
        private val task: QueueTask,
        private val world: World,
        private val npc: Npc,
        private val target: Pawn,
        private val configuration: NPCAttackConfiguration
) {


    suspend fun execute(): Boolean {
        val closeEnough = this.tryCloseDistanceWithTarget()
        if (!closeEnough) {
            LOG.debug("${this.npc} attack is not close enough to ${this.target}")
            return false
        }
        this.animateNpc()
        this.configuration.projectile?.let { cfg ->
            this.shootProjectileAtTarget(cfg)
        }
        val hit = this.prepareTargetHit()
        hit.execute()
        this.task.wait(this.configuration.nextAttackDelay)
        return true
    }

    /**
     *  Tries to close the distance with the target
     *  @return false if was unable to close the distance
     */
    private suspend fun tryCloseDistanceWithTarget(): Boolean {
        LOG.debug("${this.npc} is trying to close distance with ${this.target}")
        if (this.isTargetWithinReach()) {
            return true
        }

        if (!this.isWalkingRequired()) {
            return false
        }

        return this.walkTowardsTarget()
    }

    /**
     * Checks whether this attack can walk npc towards the target
     */
    private fun isWalkingRequired(): Boolean {
        return this.configuration.move.required
    }

    /**
     * Makes npc walk towards the target
     */
    private suspend fun walkTowardsTarget(): Boolean {
        this.npc.facePawn(this.target)
        val distance = this.getRequiredDistance()
        val projectile = this.isProjectileAttack()
        return this.npc.moveToAttackRange(this.task, this.target, distance, projectile)
    }

    /**
     * Checks whether the target is within reach of this attack
     */
    private fun isTargetWithinReach(): Boolean {
        return this.npc.tile.getDistance(this.target.tile) <= this.configuration.move.distance
    }

    /**
     * Get the required distance of this attack
     */
    private fun getRequiredDistance() = this.configuration.move.distance

    /**
     * Animates the npc with this attack animations
     */
    private fun animateNpc() {
        val attackerGraphic = this.configuration.graphic

        if (attackerGraphic != 0) {
            this.npc.graphic(attackerGraphic)
        }

        val attackAnim = this.configuration.animation
        if (attackAnim != 0) {
            this.npc.animate(attackAnim)
        }
    }

    /**
     * Prepares a hit for specific target
     */
    private fun prepareTargetHit(): NPCHit {
        LOG.debug("${this.npc} is preparing to hit ${this.target}")
        val hitDelay = this.getTargetHitDelay();
        return NPCHitFactory.create(this.world, this.npc, this.target, this.configuration.hit, hitDelay)
    }

    /**
     * The damage delay is calculated based on the ase damage delay config
     * and depending whether the projectile is settled for this method
     * a damage delay is a sum of a base delay and projectile life span
     * which is a sum of projectile delay and time it is required for the projectile
     * to arrive at target tile.
     **/
    private fun getTargetHitDelay(): Int {
        val baseDelay = this.configuration.hit.delay
        return when {
            this.isProjectileAttack() -> baseDelay + this.getProjectileHitDelay()
            else -> baseDelay
        }
    }

    /**
     * Gets the projectile's hit delay of this attack
     */
    private fun getProjectileHitDelay(): Int {
        val targetFrontTile = this.npc.getFrontFacingTile(this.target)
        val targetCentreTile = this.target.getCentreTile()
        return RangedCombatStrategy.getHitDelay(targetFrontTile, targetCentreTile)
    }

    /**
     * Check whether this attack is a projectile attack
     */
    private fun isProjectileAttack(): Boolean {
        return this.configuration.hasProjectile()
    }


    /**
     * Shoots the projectile target a target
     */
    private fun shootProjectileAtTarget(cfg: NPCAttackProjectileConfiguration) {
        LOG.debug("${this.npc} is shooting projectile at ${this.target}")
        val projectile = this.npc.createProjectile(
                this.target,
                cfg.id,
                cfg.startHeight,
                cfg.endHeight,
                cfg.delay,
                15
        )
        this.world.spawn(projectile)
    }

    companion object {
        private val LOG = LogManager.getLogger()
    }
}
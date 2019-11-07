package gg.rsmod.plugins.content.mechanics.effect.poison

import gg.rsmod.game.model.attr.AttributeKey
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.hit
import gg.rsmod.plugins.api.ext.setVarp

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Poison {
    const val POSION_VARP = 102
    val HIT_TIMER = TimerKey()
    private const val HIT_DELAY = 1
    private val HIT_VALUE = AttributeKey<Int>(persistenceKey = "posion_varp", resetOnDeath = true)

    /**
     * Applies the poison to a specific pawn
     * @return true whether the poison has been applied successfully
     *         false whether the poison has not been applied due to immunity or pending poison
     */
    fun applyPoison(pawn: Pawn, initialDamage: Int, kind: PoisonKind): Boolean {
        val appliedKind = this.getOf(pawn)
        if (appliedKind == kind || appliedKind == PoisonKind.Venom) {
            return false
        }
        if (this.isImmune(pawn)) {
            this.removeFrom(pawn)
            if (pawn is Player) {
                this.updatePlayerOrb(pawn)
            }
            return false
        }
        val initialValue = kind.getVarValue(initialDamage)
        this.updateHitValueFor(pawn, initialValue)
        this.scheduleNextHitFor(pawn)
        return true
    }

    /**
     * Removes the poison from a pawn
     */
    fun removeFrom(pawn: Pawn) {
        this.updateHitValueFor(pawn, 0)
        pawn.timers[HIT_TIMER] = 0
    }

    /**
     * Checks whether the pawn is currently poisoned
     */
    private fun isAppliedTo(pawn: Pawn): Boolean {
        return this.getHitValueOf(pawn) > 0
    }

    private fun getOf(pawn: Pawn): PoisonKind? {
        if (!this.isAppliedTo(pawn)) {
            return null
        }
        val hitValue = this.getHitValueOf(pawn)
        return PoisonKind.getByHitValue(hitValue)
    }

    /**
     * Sets a hit (varp) value for a pawn
     */
    private fun updateHitValueFor(pawn: Pawn, value: Int) {
        pawn.attr[HIT_VALUE] = value
        (pawn as? Player)?.let(this::updatePlayerOrb)
    }

    /**
     * Gets a hit (varp) value of a specific pawn
     */
    private fun getHitValueOf(pawn: Pawn): Int {
        return pawn.attr[HIT_VALUE] ?: 0
    }

    /**
     * Checks whether the pawn is immune to posion attacks
     */
    private fun isImmune(pawn: Pawn): Boolean = when (pawn) {
        is Player -> pawn.hasEquipped(EquipmentType.HEAD, Items.SERPENTINE_HELM, Items.TANZANITE_HELM, Items.MAGMA_HELM)
        is Npc -> pawn.combatDef.poisonImmunity
        else -> false
    }

    /**
     * Schedules next hit of the poison for a specific pawn
     */
    private fun scheduleNextHitFor(pawn: Pawn) {
        pawn.timers[HIT_TIMER] = HIT_DELAY
    }

    /**
     * Applies next poison hit for specific pawn
     */
    fun applyNextHitFor(pawn: Pawn) {
        val hitValue = getHitValueOf(pawn)
        val kind = PoisonKind.getByHitValue(hitValue)

        val damage = kind.getDamageForVarValue(hitValue)
        val hitValueDelta = kind.getDamageDeltaPerHit()

        pawn.hit(damage = damage, type = kind.hitKind)

        val nextHitValue = hitValue + hitValueDelta
        this.updateHitValueFor(pawn, nextHitValue)

        when {
            kind == PoisonKind.Regular && nextHitValue <= 0 -> {
                this.removeFrom(pawn)
                return
            }
        }

        this.scheduleNextHitFor(pawn)
    }

    /**
     * Updates player orb
     */
    private fun updatePlayerOrb(player: Player) {
        val value = player.attr[HIT_VALUE]!!
        player.setVarp(POSION_VARP, value)
    }
}
package gg.rsmod.plugins.content.mechanics.effect.poison

import gg.rsmod.game.model.attr.POISON_TICKS_LEFT_ATTR
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.timer.POISON_TIMER
import gg.rsmod.plugins.api.EquipmentType
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.hasEquipped
import gg.rsmod.plugins.api.ext.setVarp

/**
 * @author Tom <rspsmods@gmail.com>
 */
object Poison {

    fun getDamageForTicks(ticks: Int) = (ticks / 5) + 1

    fun isImmune(pawn: Pawn): Boolean = when (pawn) {
        is Player -> pawn.hasEquipped(EquipmentType.HEAD, Items.SERPENTINE_HELM, Items.TANZANITE_HELM, Items.MAGMA_HELM)
        is Npc -> pawn.combatDef.poisonImmunity
        else -> false
    }

    fun poison(pawn: Pawn, initialDamage: Int): Boolean {
        val ticks = (initialDamage * 5) - 4
        val oldDamage = getDamageForTicks(pawn.attr[POISON_TICKS_LEFT_ATTR] ?: 0)
        val newDamage = getDamageForTicks(ticks)
        if (oldDamage > newDamage) {
            return false
        }
        pawn.timers[POISON_TIMER] = 1
        pawn.attr[POISON_TICKS_LEFT_ATTR] = ticks
        return true
    }


}
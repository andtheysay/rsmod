package gg.rsmod.plugins.content.npcs.combat.formula

import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.api.PrayerIcon
import gg.rsmod.plugins.api.ext.hasPrayerIcon
import gg.rsmod.plugins.content.combat.formula.CombatFormula
import gg.rsmod.plugins.content.combat.formula.MagicCombatFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula

/// TODO core change to apply min hit?
class NPCCombatFormula(
        private val startStyle: CombatClass,
        private val endStyle: CombatClass,
        private val minHit: Int,
        private val maxHit: Int
) : CombatFormula {

    override fun getMaxHit(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double, specialPassiveMultiplier: Double): Int {
        return this.maxHit
    }

    override fun getAccuracy(pawn: Pawn, target: Pawn, specialAttackMultiplier: Double): Double {
        if (this.isTargetProtected(target as Player, this.startStyle)) {
            return 0.0
        }
        return when (this.endStyle) {
            CombatClass.MELEE -> MeleeCombatFormula.getAccuracy(pawn, target, specialAttackMultiplier)
            CombatClass.RANGED -> RangedCombatFormula.getAccuracy(pawn, target, specialAttackMultiplier)
            CombatClass.MAGIC -> MagicCombatFormula.getAccuracy(pawn, target, specialAttackMultiplier)
        }
    }

    private fun isTargetProtected(target: Player, from: CombatClass): Boolean {
        return when (from) {
            CombatClass.MELEE -> target.hasPrayerIcon(PrayerIcon.PROTECT_FROM_MELEE)
            CombatClass.RANGED -> target.hasPrayerIcon(PrayerIcon.PROTECT_FROM_MISSILES)
            CombatClass.MAGIC -> target.hasPrayerIcon(PrayerIcon.PROTECT_FROM_MAGIC)
        }
    }

}
package gg.rsmod.plugins.content.npcs.combat.configuration

import gg.rsmod.game.model.combat.CombatStyle

data class AttackMethod(
        val weight: Double,
        val startingAttackStyle: CombatStyle,
        val endingAttackStyle: CombatStyle,
        val damageDelay: Int,
        val nextAttackDelay: Int,
        val poisonChance: Double,
        val attackDistance: Int,
        val moveToAttack: Boolean,
        val minHit: Int,
        val maxHit: Int,
        val attackAnim: Int,
        val attackSound: Int,
        val attackerGraphic: Int,
        val targetGraphic: Int
)
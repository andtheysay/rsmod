package gg.rsmod.plugins.content.npcs.combat.configuration

import com.google.gson.annotations.SerializedName
import gg.rsmod.game.model.combat.CombatClass

data class NPCAttackConfiguration(
        val weight: Double,
        val startingAttackStyle: CombatClass,
        val endingAttackStyle: CombatClass,
        val damageDelay: Int,
        val nextAttackDelay: Int,
        val poisonChance: Double,
        val poisonDamage: Int,
        val attackDistance: Int,
        val moveToAttack: Boolean,
        val minHit: Int,
        val maxHit: Int,
        val attackAnim: Int,
        val attackSound: Int,
        val attackerGraphic: Int,
        val targetGraphic: Int,

        @SerializedName("projectileId")
        val projectile: Int,
        val projectileHeight: Int,
        val projectileStartHeight: Int,
        val projectileEndHeight: Int,
        val projectileDelay: Int
) {
    fun hasProjectile() = this.projectile != 0
}
package gg.rsmod.plugins.content.npcs.combat.configuration.attack

import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.NPCTargetHitConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.projectile.NPCAttackProjectileConfiguration

data class NPCAttackConfiguration(
        val weight: Double,
        val animation: Int,
        val sound: Int,
        val graphic: Int,
        val nextAttackDelay: Int,

        val hit: NPCTargetHitConfiguration,
        val move: NPCMoveConfiguration,
        val projectile: NPCAttackProjectileConfiguration?

) {
    fun hasProjectile() = this.projectile != null
}
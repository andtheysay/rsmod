package gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit

import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.HitEffectConfiguration

data class NPCTargetHitConfiguration(
        val style: NPCTargetHitStyleConfiguration,
        val effects: List<HitEffectConfiguration>?,
        val min: Int,
        val max: Int,
        val delay: Int
)
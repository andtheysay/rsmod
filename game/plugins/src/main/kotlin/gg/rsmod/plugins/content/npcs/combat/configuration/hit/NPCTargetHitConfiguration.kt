package gg.rsmod.plugins.content.npcs.combat.configuration.hit

import gg.rsmod.plugins.content.npcs.combat.configuration.hit.effect.HitEffect

data class NPCTargetHitConfiguration(
        val blocked: HitEffect,
        val landed: HitEffect
)
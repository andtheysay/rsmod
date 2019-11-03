package gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.stat

import gg.rsmod.plugins.api.Skills

enum class StatKind(val raw: Int) {
    Attack(Skills.ATTACK),
    Defence(Skills.DEFENCE),
    Strength(Skills.STRENGTH),
    Hitpoints(Skills.HITPOINTS),
    Prayer(Skills.PRAYER),
    Ranged(Skills.RANGED),
    Magic(Skills.MAGIC)
}
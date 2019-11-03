package gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.stat

import gg.rsmod.game.model.entity.Pawn
import gg.rsmod.game.model.entity.Player
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.stat.AlterStatEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.core.attack.hit.effect.NPCHitEffect

class AlterStatEffect(private val cfg: AlterStatEffectConfiguration) : NPCHitEffect {
    override fun apply(target: Pawn) {
        (target as? Player)?.let {
            for ((skill, alter) in this.cfg.values) {
                val id = skill.raw
                target.getSkills().alterCurrentLevel(id, alter)
            }
        }
    }
}
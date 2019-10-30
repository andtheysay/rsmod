package gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect

import com.google.gson.JsonObject

data class HitEffectConfiguration(
        val predicate: HitEffectPredicate,
        val kind: HitEffectKind,
        private val cfg: JsonObject
) {

    val configuration: Any
        get() = this.kind.configurationFromRaw(this.cfg)
}

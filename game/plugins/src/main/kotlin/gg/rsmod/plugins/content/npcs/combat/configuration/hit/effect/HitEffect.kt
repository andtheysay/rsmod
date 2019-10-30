package gg.rsmod.plugins.content.npcs.combat.configuration.hit.effect

import com.google.gson.JsonObject

data class HitEffect(
        val kind: HitEffectKind,
        private val cfg: JsonObject
) {

    val configuration: HitEffectConfiguration by lazy {
        this.kind.configurationFromRaw(this.cfg)
    }
}

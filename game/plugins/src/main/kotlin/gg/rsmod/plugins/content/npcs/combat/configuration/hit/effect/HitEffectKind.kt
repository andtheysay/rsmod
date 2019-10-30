package gg.rsmod.plugins.content.npcs.combat.configuration.hit.effect

import com.google.gson.Gson
import com.google.gson.JsonObject
import gg.rsmod.plugins.content.npcs.combat.configuration.hit.effect.poison.PoisonConfiguration

enum class HitEffectKind(private val cfgClass: Class<out HitEffectConfiguration>) {
    Poison(PoisonConfiguration::class.java);

    fun configurationFromRaw(obj: JsonObject): HitEffectConfiguration {
        return Gson().fromJson(obj, this.cfgClass)
    }
}
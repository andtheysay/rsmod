package gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect

import com.google.gson.Gson
import com.google.gson.JsonObject
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.AnimateEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.basic.GraphicEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.freeze.FreezeEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.poison.PoisonEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.stat.AlterStatEffectConfiguration
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.hit.effect.venom.VenomEffectConfiguration

enum class HitEffectKind(private val cfgClass: Class<*>) {
    Freeze(FreezeEffectConfiguration::class.java),
    Poison(PoisonEffectConfiguration::class.java),
    Venom(VenomEffectConfiguration::class.java),
    AlterStat(AlterStatEffectConfiguration::class.java),
    Graphic(GraphicEffectConfiguration::class.java),
    Animate(AnimateEffectConfiguration::class.java);

    fun configurationFromRaw(obj: JsonObject): Any {
        return Gson().fromJson(obj, this.cfgClass)
    }
}
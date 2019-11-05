package gg.rsmod.plugins.content.mechanics.effect.poison

import gg.rsmod.plugins.api.HitType
import kotlin.math.ceil

enum class PoisonKind(val hitKind: HitType) {
    Regular(HitType.POISON),
    Venom(HitType.VENOM);

    fun getVarValue(initialDamage: Int): Int {
        return when (this) {
            Regular -> ceil(initialDamage * 5.0).toInt()
            Venom -> 1_000_000 + (initialDamage / 2)
        }
    }


    fun getDamageForVarValue(value: Int): Int {
        return when (this) {
            Regular -> ceil(value / 5.0).toInt()
            Venom -> (value - 1_000_000) * 2
        }
    }

    fun getDamageDeltaPerHit(): Int {
        return when (this) {
            // Regular poison damage is decreasing per hit
            Regular -> -1
            // Venom damage is increasing per hit
            Venom -> 1
        }
    }

    companion object {
        fun getByHitValue(value: Int): PoisonKind {
            return when {
                value >= 1_000_000 -> Venom
                else -> Regular
            }
        }
    }
}


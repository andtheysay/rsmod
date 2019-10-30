package gg.rsmod.plugins.content.npcs.combat.configuration

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import gg.rsmod.plugins.api.NpcSpecies
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

data class NPCCombatConfiguration(
        val name: String,
        val ids: Set<Int>,
        @SerializedName("npcspecies")
        val species: List<NpcSpecies>,
        val attackSpeed: Int,
        val respawnDelay: Int,

        val combatLevel: Int,
        val hitpoints: Int,
        val attack: Int,
        val strength: Int,
        val defence: Int,
        val magic: Int,
        val ranged: Int,

        val defenceStabBonus: Int,
        val defenceSlashBonus: Int,
        val defenceCrushBonus: Int,
        val defenceMagicBonus: Int,
        val defenceRangedBonus: Int,

        val blockAnim: Int,

        val deathAnim: Int,
        val deathSound: Int,

        val aggroRadius: Int,
        val aggroSearchDelay: Int,
        val aggroTimer: Int,

        val slayerXP: Int,

        val attackMethods: List<NPCAttackConfiguration>
) {

    companion object {
        private const val DEFAULT_CONFIGURATION_DIRECTORY = "./data/cfg/combat/"
        private val GSON = Gson()

        /**
         * Loads all the configuration from json files placed under path
         */
        fun loadAll(directory: String = DEFAULT_CONFIGURATION_DIRECTORY): List<NPCCombatConfiguration> {
            return (Paths.get(directory).toFile().listFiles() ?: emptyArray<File>())
                    .filter { it.name.endsWith(".json") }
                    .map(::load)
        }

        /**
         *  Loads the configuration from json file
         */
        fun load(file: File): NPCCombatConfiguration {
            val reader = BufferedReader(FileReader(file))
            return GSON.fromJson(reader, NPCCombatConfiguration::class.java)
        }
    }
}
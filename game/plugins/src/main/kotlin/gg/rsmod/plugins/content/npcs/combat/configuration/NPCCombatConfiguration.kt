package gg.rsmod.plugins.content.npcs.combat.configuration

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import gg.rsmod.plugins.api.NpcSpecies
import gg.rsmod.plugins.content.npcs.combat.configuration.attack.NPCAttackConfiguration
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

data class NPCCombatConfiguration(
        val name: String,
        val ids: Set<Int>,
        val species: List<NpcSpecies>,
        val slayerXP: Int,
        val attackSpeed: Int,
        val respawn: NPCCombatRespawnConfiguration,
        val stats: NPCCombatStats,
        val defenceBonuses: NPCCombatDefenceBonuses,
        val death: NPCCombatDeathConfiguration,
        val aggression: NPCCombatAggressionConfiguration,
        val attacks: List<NPCAttackConfiguration>,
        val block: NPCCombatBlockConfiguration
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
package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.game.model.item.Item
import java.util.*


/**
 * @author Lantern Web
 */
enum class FishingSpotType(val level: Int, val xp: Double, val bait: Int, val depleteChance: Int, val respawnTime: IntRange, val tools: Array<Item>) {

    //TODO: petrate, cluerate, depletions, respawntimers
    SHRIMPS(level = 1, xp = 25.0, bait = 1511, depleteChance = 1, respawnTime = 15..25, tools = arrayOf(Item(1, 1)));

    private val fishingSpot = HashMap<Int, FishingSpotType>()




}
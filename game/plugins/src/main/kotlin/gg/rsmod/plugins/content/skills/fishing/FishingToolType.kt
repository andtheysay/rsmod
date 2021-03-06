package gg.rsmod.plugins.content.skills.fishing

import gg.rsmod.game.model.item.Item
import gg.rsmod.plugins.api.cfg.Items


/**
 * @author Lantern Web
 */
enum class FishingToolType(val item: Int, val level: Int, val animation: Int, val speed: Int, val catches: Array<Item>) {
    SMALL_NET(item = Items.SMALL_FISHING_NET, level = 1, animation = 621, speed = 3, catches = arrayOf(Item(995,1), Item(995,1)));
    //BIG_NET(item = Items.SMALL_FISHING_NET, level = 16, animation = 621, speed = 3, catches = arrayOf(Item(995,1)),
    //FISHING_ROD(item = Items.FISHING_ROD, level = 5, animation = 622, speed = 1, catches = arrayOf(Item(995,1))),
    //FLY_FISHING_ROD(item = Items.FLY_FISHING_ROD, level = 20, animation = 622, speed = 1, catches = arrayOf(Item(995,1)),
    //HARPOON(item = Items.HARPOON, level = 35, animation = 618, speed = 4, catches = arrayOf(Item(995,1)),
    //LOBSTER_POT(item = Items.SMALL_FISHING_NET, level = 40, animation = 619, speed = 4, catches = arrayOf(Item(995,1))

    companion object {
        val values = enumValues<FishingToolType>()
    }
}
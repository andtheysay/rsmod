package gg.rsmod.game.model.droptable

import gg.rsmod.game.model.item.Item
import org.json.JSONObject

class ItemDrop(json: String): JSONObject(json) {
    var itemId: Int = this.optInt("itemId")
    var quanityMin: Int = this.optInt("quanityMin")
    var quanityMax: Int = this.optInt("quanityMax")

}
/*
val CluesBeginner = arrayOf(
        /**Beginner Scroll*/ 23182,
        /**Beginner Nests*/ 23127, 23128,
        /**Beginner Geodes*/ 23442, 23443,
        /**Beginner Bottles*/ 23129, 23130
        )

val CluesEasy = arrayOf(
        /**Easy Scroll*/ 2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685, 2686, 2687, 2688, 2689, 2690, 2691, 2692,
        2693, 2694, 2695, 2696, 2697, 2698, 2699, 2700, 2701, 2702, 2703, 2704, 2705, 2706, 2707, 2708, 2709, 2710, 2711,
        2712, 2713, 2716, 2719, 3490, 3491, 3492, 3494, 3495, 3496, 3497, 3498, 3499, 3500, 3501, 3502, 3503, 3504, 3505,
        3506, 3507, 3508, 3509, 3510, 3512, 3513, 3514, 3515, 3516, 3518, 7236, 7238, 10180, 10182, 10184, 10186, 10188,
        10190, 10192, 10194, 10196, 10198, 10200, 10202, 10204, 10206, 10208, 10210, 10212, 10214, 10216, 10218, 10220,
        10222, 10224, 10226, 10228, 10230, 10232, 12162, 12164, 12166, 12167, 12168, 12169, 12170, 12172, 12173, 12174,
        12175, 12176, 12177, 12178, 12179, 12181, 12182, 12183, 12184, 12185, 12186, 12187, 12188, 12189, 12190, 12191,
        12192, 19814, 19816, 19817, 19818, 19819, 19820, 19821, 19822, 19823, 19824, 19825, 19826, 19828, 19829, 19830,
        19831, 19833, 22001, 23149, 23150, 23151, 23152, 23153, 23154, 23155, 23156, 23157, 23158, 23159, 23160, 23161,
        23162, 23163, 23164, 23165, 23166,
        /**Easy Nests*/ 19712, 19713,
        /**Easy Geodes*/ 20358, 20359,
        /**Easy Bottles*/ 13648, 15484
        )
*/
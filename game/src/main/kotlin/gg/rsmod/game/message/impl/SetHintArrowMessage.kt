package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.entity.Pawn

class SetHintArrowMessage(val type: Int, val target: Pawn?, val location: Tile?) : Message

enum class ArrowOffsetType {
    CENTER,
    NORTHWEST,
    SOUTHWEST,
    NORTHEAST,
    SOUTHEAST
}


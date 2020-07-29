package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.SetHintArrowMessage
import java.lang.Exception

class HintArrowEncoder : MessageEncoder<SetHintArrowMessage>() {
    override fun extract(message: SetHintArrowMessage, key: String): Number = when(key) {
        "target_type" -> message.type
        "index_or_tile_x" -> {
            if (message.type == 1 || message.type == 10) {
                checkNotNull(message.target)
                message.target.index
            } else if(message.type in 2..6){
                checkNotNull(message.location)
                message.location.x
            } else {
                0
            }
        }
        "tile_z" -> {
            if(message.type in 2..6) {
                checkNotNull(message.location)
                message.location.z
            } else {
                0
            }
        }
        "tile_height" -> {
            if(message.type in 2..6) {
                checkNotNull(message.location)
                message.location.height
            } else {
                0
            }
        }
        else -> throw Exception("Invalid hint_arrow key")
    }

    override fun extractBytes(message: SetHintArrowMessage, key: String): ByteArray = throw Exception("hint_arrow should not use type BYTES")
}
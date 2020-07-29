package gg.rsmod.game.model.container.listener

import gg.rsmod.game.model.entity.Player

/**
 * @author Tom <rspsmods@gmail.com>
 */
object WeightContainerListener : ItemContainerListener {

    override fun clean(player: Player) {
        player.calculateWeight()
    }
}
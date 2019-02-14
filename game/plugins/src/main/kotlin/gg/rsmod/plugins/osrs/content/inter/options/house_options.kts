package gg.rsmod.plugins.osrs.content.inter.options

import gg.rsmod.plugins.osrs.api.InterfaceDestination
import gg.rsmod.plugins.osrs.api.ext.closeInterface
import gg.rsmod.plugins.osrs.api.ext.openInterface
import gg.rsmod.plugins.osrs.api.ext.player
import gg.rsmod.plugins.osrs.api.ext.setComponentText

on_button(parent = OptionsTab.INTERFACE_ID, child = 98) {
    /**
     Teleport inside + doors: varp 1047
     */
    val p = it.player()
    if (!p.lock.canComponentInteract()) {
        return@on_button
    }
    p.openInterface(component = 370, pane = InterfaceDestination.TAB_AREA)
    p.setComponentText(parent = 370, child = 20, text = "Number of rooms: 9")
}

on_button(parent = 370, child = 21) {
    it.player().closeInterface(interfaceId = 370)
}
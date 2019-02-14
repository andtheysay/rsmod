package gg.rsmod.plugins.osrs.content.inter.worldmap

import gg.rsmod.plugins.osrs.api.InterfaceDestination
import gg.rsmod.plugins.osrs.api.ext.*

on_button(parent = 160, child = 42) {
    val p = it.player()
    if (!p.lock.canComponentInteract()) {
        return@on_button
    }

    if (!p.isInterfaceVisible(WorldMap.INTERFACE_ID)) {
        val opt = it.getInteractingOption()
        p.sendWorldMapTile()
        p.openInterface(component = WorldMap.INTERFACE_ID, pane = InterfaceDestination.WORLD_MAP, fullscreen = opt == 1)
        if (opt == 1) {
            p.openInterface(component = WorldMap.FULLSCREEN_INTERFACE_ID, pane = InterfaceDestination.WORLD_MAP_FULL, fullscreen = true)
        }
        p.setInterfaceEvents(parent = WorldMap.INTERFACE_ID, child = 20, range = 0..4, setting = 2)
        p.timers[WorldMap.UPDATE_TIMER] = 1
    } else {
        p.closeInterface(WorldMap.INTERFACE_ID)
    }
}

on_button(parent = WorldMap.INTERFACE_ID, child = 37) {
    val p = it.player()
    p.closeInterface(WorldMap.INTERFACE_ID)
    p.openOverlayInterface(p.components.displayMode)
    p.attr.remove(WorldMap.LAST_TILE)
    p.timers.remove(WorldMap.UPDATE_TIMER)
}

on_timer(WorldMap.UPDATE_TIMER) {
    val p = it.player()

    if (p.isInterfaceVisible(WorldMap.INTERFACE_ID)) {
        /**
         * Only send the world when the last tile recorded is not the same as
         * the current one being stood on, so we're not needlessly sending the
         * script every cycle.
         */
        val lastTile = p.attr[WorldMap.LAST_TILE]
        if (lastTile == null || !lastTile.sameAs(p.tile)) {
            p.sendWorldMapTile()
            p.attr[WorldMap.LAST_TILE] = p.tile
        }

        p.timers[WorldMap.UPDATE_TIMER] = 1
    }
}
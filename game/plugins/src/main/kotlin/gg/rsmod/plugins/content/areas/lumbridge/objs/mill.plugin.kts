package gg.rsmod.plugins.content.areas.lumbridge.objs

val millStatus = AttributeKey<Int>("MILL_LUMBRIDGE", false)
val LADDER_CLIMB = 828
val FILL_HOPPER = 3572
val BIN_EMPTY = Objs.FLOUR_BIN_5792
val BIN_FULL = Objs.FLOUR_BIN
val LEVER_ORIGINAL = Objs.HOPPER_CONTROLS_24964
val LEVER_PULLING = Objs.HOPPER_CONTROLS_24967

on_obj_option(BIN_EMPTY, "empty") {
    player.queue {
        player.message("The flour bin is already empty. You need to place wheat in the hopper upstairs first.")
    }
}

on_obj_option(BIN_FULL, "empty") {
    player.queue {
        if (player.inventory.contains(Items.POT)) {
            player.message("You fill a pot with the last of the flour in the bin.")
            world.spawn(DynamicObject(player.getInteractingGameObj(), BIN_EMPTY))
            player.inventory.remove(Items.POT, 1)
            player.inventory.add(Items.POT_OF_FLOUR, 1)
            player.attr.remove(millStatus)
        } else
            player.message("You need an empty pot to hold flour in.")
    }
}

on_obj_option(LEVER_ORIGINAL, "operate") {
    player.queue {
        if (player.attr[millStatus] != 0)
            player.message("You operate the empty hopper. Nothing interesting happens.")
        else {
            player.message("You operate the hopper. The grain slides down the chute.")
            player.attr[millStatus] = 1
            world.spawn(DynamicObject(player.getInteractingGameObj(), LEVER_PULLING))
            world.spawn(DynamicObject(world.getObject(Tile(3166, 3306), 10)!!, BIN_FULL))
            player.animate(FILL_HOPPER)
            player.lock()
            wait(2)
            world.spawn(DynamicObject(player.getInteractingGameObj(), LEVER_ORIGINAL))
            player.unlock()
        }
    }
}

on_obj_option(Objs.HOPPER_24961, "fill") {
    player.queue {
        if (player.attr.has(millStatus)) {
            player.message("There is already grain in the hopper.")
            return@queue
        }
        if (!player.inventory.contains(Items.GRAIN)) {
            player.message("You haven't got anything to fill the hopper with.")
            return@queue
        }
        player.message("You put the grain in the hopper. You should now pull the lever nearby to operate the hopper.")
        player.inventory.remove(Items.GRAIN, 1)
        player.attr.put(millStatus, 0)
        player.animate(FILL_HOPPER)
        player.lock()
        wait(1)
        player.unlock()
    }
}

//Ladder
//1st Floor
on_obj_option(Objs.LADDER_12964, "climb-up") {
    player.queue {
        player.animate(LADDER_CLIMB)
        player.lock()
        wait(2)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z + 1)
        player.unlock()
    }
}

//2nd Floor
on_obj_option(Objs.LADDER_12965, "climb-up") {
    player.queue {
        player.animate(LADDER_CLIMB)
        player.lock()
        wait(2)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z + 1)
        player.unlock()
    }
}

on_obj_option(Objs.LADDER_12965, "climb-down") {
    player.queue {
        player.animate(LADDER_CLIMB)
        player.lock()
        wait(2)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z - 1)
        player.unlock()
    }
}

//3rd Floor
on_obj_option(Objs.LADDER_12966, "climb-down") {
    player.queue {
        player.animate(LADDER_CLIMB)
        player.lock()
        wait(2)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z - 1)
        player.unlock()
    }
}
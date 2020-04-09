package gg.rsmod.plugins.content.areas.lumbridge.objs

//Bottom Floor
on_obj_option(Objs.STAIRCASE_16671, "climb-up") {
    player.queue {
        player.lock()
        wait(1)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z+1)
        player.unlock()
    }
}

//2nd Floor
on_obj_option(Objs.STAIRCASE_16672, "climb-up") {
    player.queue {
        player.lock()
        wait(1)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z+1)
        player.unlock()
    }
}

on_obj_option(Objs.STAIRCASE_16672, "climb-down") {
    player.queue {
        player.lock()
        wait(1)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z-1)
        player.unlock()
    }
}

//3rd Floor
on_obj_option(Objs.STAIRCASE_16673, "climb-down") {
    player.queue {
        player.lock()
        wait(1)
        player.moveTo(player.tile.x, player.tile.y, player.tile.z-1)
        player.unlock()
    }
}
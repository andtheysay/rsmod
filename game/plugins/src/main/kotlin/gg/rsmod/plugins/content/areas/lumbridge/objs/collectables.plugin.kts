package gg.rsmod.plugins.content.areas.lumbridge.objs

val WHEAT_1 = Objs.WHEAT_15506
val WHEAT_2 = Objs.WHEAT_15507
val ANIM_HARVEST = 827

//Wheat
on_obj_option(WHEAT_1, "pick") {
    val gameObject = player.getInteractingGameObj()
    if (player.inventory.hasSpace)
    {
        player.queue {
            player.message("You pick some grain.")
            player.animate(ANIM_HARVEST)
            player.lock()
            wait(2)
            player.unlock()
            world.queue {
                val newSpawn = DynamicObject(world.getObject(gameObject.tile, 10)!!, WHEAT_1)
                world.remove(gameObject)
                wait(15)
                world.spawn(newSpawn)
            }
            player.inventory.add(Items.GRAIN)
        }
    }
    else
    {
        player.message("You can't carry any more grain.")
    }
}

on_obj_option(WHEAT_2, "pick") {
    val gameObject = player.getInteractingGameObj()
    if (player.inventory.hasSpace)
    {
        player.queue {
            player.message("You pick some grain.")
            player.animate(ANIM_HARVEST)
            player.lock()
            wait(2)
            player.unlock()
            world.queue {
                val newSpawn = DynamicObject(world.getObject(gameObject.tile, 10)!!, WHEAT_2)
                world.remove(gameObject)
                wait(15)
                world.spawn(newSpawn)
            }
            player.inventory.add(Items.GRAIN)
        }
    }
    else
    {
        player.message("You can't carry any more grain.")
    }
}
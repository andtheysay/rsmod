package gg.rsmod.plugins.content.items.tools

on_item_option(Items.POT_OF_FLOUR, "empty") {
    player.queue {
        player.message("You empty the contents of the pot on the floor")
        player.inventory.remove(Items.POT_OF_FLOUR)
        player.inventory.add(Items.POT)
    }
}
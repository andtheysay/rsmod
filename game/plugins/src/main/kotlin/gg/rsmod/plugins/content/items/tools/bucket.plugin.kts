package gg.rsmod.plugins.content.items.tools

val SOUND_EMPTY_BUCKET = 2401

on_item_option(Items.BUCKET_OF_MILK, "empty") {
    player.queue {
        player.message("You empty the contents of the bucket on the floor")
        player.playSound(SOUND_EMPTY_BUCKET)
        player.inventory.remove(Items.BUCKET_OF_MILK)
        player.inventory.add(Items.BUCKET)
    }
}
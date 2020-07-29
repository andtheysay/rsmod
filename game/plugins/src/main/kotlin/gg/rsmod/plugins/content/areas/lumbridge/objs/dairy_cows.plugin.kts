package gg.rsmod.plugins.content.areas.lumbridge.objs

val SOUND_MILKING = 372
val ANIM_MILKING = 2305

on_obj_option(Objs.DAIRY_COW, "milk") {
    player.queue {
        if (player.inventory.contains(Items.BUCKET)) {
            player.message("You milk the cow.")
            player.inventory.remove(Items.BUCKET, 1)
            player.inventory.add(Items.BUCKET_OF_MILK, 1)
            player.lock()
            player.animate(ANIM_MILKING)
            player.playSound(SOUND_MILKING)
            wait(4)
            player.unlock()
        } else
            player.message("TODO: fix this dialog - need a bucket")
    }
}
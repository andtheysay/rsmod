package gg.rsmod.plugins.content.areas.lumbridge.chat

arrayOf(Npcs.BOB_2812).forEach { shop ->
    on_npc_option(npc = shop, option = "talk-to") {
        player.queue { dialog(this) }
    }

    on_npc_option(npc = shop, option = "trade") {
        open_shop(player)
    }

    on_npc_option(npc = shop, option = "repair") {
        player.queue { repairDialog(this) }
    }
}

suspend fun dialog(it: QueueTask) {
    when (it.options("Give me a quest!", "Have you anything to sell?", "Can you repair my items for me?")) {
        1 -> {
            it.chatPlayer("Give me a quest!", animation = 588)
            it.chatNpc("Get yer own!", animation = 567)
        }
        2 -> {
            it.chatPlayer("Have you anything to sell?", animation = 588)
            it.chatNpc("Yes! I buy and sell axes! Take your ppick (or axe)!", animation = 567)
            open_shop(it.player)
        }
        3 -> {
            it.chatPlayer("Can you repair my items for me?", animation = 588)
            it.chatNpc("Of course I'll repair it, though the materials may cost you. Just hand me the item and I'll have a look.", animation = 567)
        }
    }
}

suspend fun repairDialog(it: QueueTask) {
    it.chatNpc("You don't have anything I can repair.", animation = 567)
}

fun open_shop(p: Player) {
    p.openShop("Bob's Brilliant Axes")
}
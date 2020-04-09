package gg.rsmod.plugins.content.areas.lumbridge.chat

arrayOf(Npcs.PRAYER_TUTOR).forEach { tutor ->
    on_npc_option(npc = tutor, option = "talk-to") {
        player.queue { dialog(this) }
    }
}

suspend fun dialog(it: QueueTask) {
    when (it.options("Can you teach me the basics of prayer please?", "Tell me about different bones.", "Goodbye.")) {
        1 -> {
            it.chatPlayer("Can you teach me the basics of prayer please?", animation = 588)
            it.chatNpc("Of course young on. You can gain prayer experience by burying bones. Most of the wretched creatures around the lands will leave behind bones after you kille them, simply pick them up then click on the in your", animation = 567)
            it.chatNpc("inventory to bury them.", animation = 567)
            it.chatNpc("Prayers benefit you by making you stronger, quicker, or protecting an item when you die, wonderful things like that. The first one you will already have straight after leaving the tutorial. It's called Thick Skin.", animation = 567)
            it.chatNpc("Check the Prayer side-panel to see your prayers.", animation = 567)
            it.chatNpc("To use a prayer, simply click on the prayer to activate it. It will light up and your prayer points will drain away slowly. To turn it off, simply click it again.", animation = 567)
            it.chatNpc("When you run out of prayer points, the prayer will stop, and you will need to recharge at an altar such as the on in this chapel.", animation = 567)
            dialog(it)
        }
        2 -> {
            it.chatPlayer("Tell me about different bones.", animation = 588)
            when (it.options("Basic Bones", "Big Bones", "Goodbye.")) {
                1 -> {
                    it.chatNpc("Basic bones are left by many creatures such as goblins, monkeys and that sort of thing. They won't get you much when you bury them, but if you do it every time you come across them, it all adds up!", animation = 567)
                }
                2 -> {
                    it.chatNpc("Big bones you can get by killing things like ogres and giants, them being big things and all. They're quite a good boost for your prayer if you are up to fighting the big boys.", animation = 567)
                    it.chatNpc("You can probably find them in caves and such dark dank places", animation = 567)
                }
                3 -> {
                    goodbye(it)
                }
            }
        }
        3 -> {
            goodbye(it)
        }
    }
}

suspend fun goodbye(it: QueueTask) {
    it.chatPlayer("Goodbye.", animation = 588)
}
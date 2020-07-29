package gg.rsmod.plugins.content.areas.lumbridge.chat

val cooksAssistantProgress = AttributeKey<Int>("QUEST_F2P_COOKS_ASSISTANT", false)
val cooksAssistantMilk = AttributeKey<Boolean>("QUEST_F2P_COOKS_ASSISTANT_MILK", false)
val cooksAssistantFlour = AttributeKey<Boolean>("QUEST_F2P_COOKS_ASSISTANT_FLOUR", false)
val cooksAssistantEgg = AttributeKey<Boolean>("QUEST_F2P_COOKS_ASSISTANT_EGG", false)
var requestedInformation = 0

on_npc_option(npc = Npcs.COOK_4626, option = "talk-to") {
    player.queue { dialog(this) }
}

suspend fun dialog(it: QueueTask) {
    if (!it.player.attr.has(cooksAssistantProgress))
        introDialog(it)
    else
    {
        if (it.player.attr[cooksAssistantProgress] == 0)
            cooksAssistantDialog(it)
    }
}

suspend fun introDialog(it: QueueTask) {
    it.chatNpc("What am I to do?", animation = 567)
    when (it.options("What's wrong?", "Can you make me a cake?", "You don't look very happy.", "Nice hat!")) {
        1 -> startCooksAssistant(it)
        2 -> {
            it.chatPlayer("You're a cook, why don't you bake me a cake?", animation = 588)
            it.chatNpc("*sniff* Don't talk to me about cakes...", animation = 567)
            startCooksAssistant(it)
        }
        3 -> {
            it.chatPlayer("You don't look very happy.", animation = 588)
            it.chatNpc("No I'm not. The world is caving in around me - I am overcome by dark feelings of impending doom.", animation = 567)
            when (it.options("What's wrong?", "I'd take the rest of the day off if I were you.")) {
                1 -> {
                    startCooksAssistant(it)
                }
                2 -> {
                    it.chatPlayer("I'd take the rest of the day off if I were you.", animation = 588)
                    it.chatNpc("No, that's the worst thing I could do. I'd get in terrible trouble.", animation = 567)
                    it.chatPlayer("Well maybe you need to take a holiday...", animation = 588)
                    it.chatNpc("That would be nice, but the duke doesn't allow holidays for core staff.", animation = 567)
                    it.chatPlayer("Hmm, why not run away to the sea and start a new life as a Pirate?", animation = 588)
                    it.chatNpc("My wife gets sea sick, and I have an irrational fear of eyepatches. I don't see it working myself.", animation = 567)
                    it.chatPlayer("I'm afraid I've run out of ideas.", animation = 588)
                    it.chatNpc("I know I'm doomed.", animation = 567)
                    startCooksAssistant(it)
                }
            }
        }
        4 -> {
            it.chatPlayer("Nice hat!", animation = 588)
            it.chatNpc("Err thank you. It's a pretty ordinary cooks hat really.", animation = 567)
            it.chatPlayer("Still, suits you, The trousers are pretty special too.", animation = 588)
            it.chatNpc("It's all standard cook's issue uniform...", animation = 567)
            it.chatPlayer("The whole hat, apron, stripey trousers ensemble - it works. It makes you look like a real cook.", animation = 588)
            it.chatNpc("I am a real cook! I haven't got time to talk about culinary fashion. I am in desperate need of help!", animation = 567)
            startCooksAssistant(it)
        }
    }
}

suspend fun startCooksAssistant(it: QueueTask) {
    it.chatPlayer("What's wrong?", animation = 588)
    it.chatNpc("Oh dear, oh dear, oh dear, I'm in a terrible terrible mess! It's the Duke's birthday today, and I should be making him a lovely big birthday cake.", animation = 567)
    it.chatNpc("I've forgotten to buy the ingredients. I'll never get them in time now. He'll sack me! What will I do? I have four children and a goat to look after. Would you help me? Please?", animation = 567)
    when (it.options("I'm always happy to help a cook in distress.", "I can't right now, Maybe later")) {
        1 -> {
            it.chatPlayer("Yes, I'll help you.", animation = 588)
            it.player.attr.put(cooksAssistantProgress, 0)
            it.player.attr.put(cooksAssistantMilk, false)
            it.player.attr.put(cooksAssistantFlour, false)
            it.player.attr.put(cooksAssistantEgg, false)
            it.chatNpc("Oh thank you, thank you, I need milk, an egg, and flour. I'd be very grateful if you can get them for me.", animation = 567)
            helpFindIngredients(it)
        }
        2 -> {
            it.chatPlayer("No, I don't feel like it. Maybe later.", animation = 588)
            it.chatNpc("Fine. I always knew you Adventurer types were callous beasts. Go on your merry way!", animation = 567)
        }
    }
}

suspend fun helpFindIngredients(it: QueueTask) {
    var dynamicOption = "Actually, I know where to find this stuff."
    if (requestedInformation>2)
        dynamicOption = "I've got all the information I need. Thanks."

    it.chatPlayer("So where do I find these ingredients then?", animation = 588)
    when (it.options("Where do I find some flour?", "How about milk?", "And eggs? Where are they found?", dynamicOption)) {
        1 -> {
            it.chatNpc("There is a Mill fairly close. Go North and then West, Mill Lane Mill is just off the road to Draynor. I usually get my flour from there.", animation = 567)
            if (it.player.inventory.contains(Items.POT))
                it.chatNpc("Talk to Millie. She'll help, she's a lovely girl and a fine Miller. Make sure you take a pot with you for the flour though, you've got one on you already.", animation = 567)
            else
                it.chatNpc("Talk to Millie. She'll help, she's a lovely girl and a fine Miller. Make sure you take a pot with you for the flour though, there should be one on the table in here.", animation = 567)
            requestedInformation++
            helpFindIngredients(it)
        }
        2 -> {
            it.chatNpc("There is a cattle field on the other side of the river, just across the road from the Groats' Farm.", animation = 567)
            it.chatNpc("Talk to Gillie Groats, she looks after the Dairy cows - she'll tell you everything you need to know about milking cows!", animation = 567)
            if (it.player.inventory.contains(Items.BUCKET))
                it.chatNpc("You'll need an empty bucket for the milk itself. I do see you've got a bucket with you already luckily!", animation = 567)
            else
                it.chatNpc("You'll need an empty bucket for the milk itself. The general store just north of the castle will sell you one for a couple of coins.", animation = 567)
            requestedInformation++
            helpFindIngredients(it)
        }
        3 -> {
            it.chatNpc("I normally get my eggs from the Groats' farm, on the other side of the river.", animation = 567)
            it.chatNpc("But any chicken should lay eggs.", animation = 567)
            requestedInformation++
            helpFindIngredients(it)
        }
        4 -> {
            if (requestedInformation>2)
                it.chatPlayer("I've got all the information I need. Thanks.", animation = 588)
            else
                it.chatPlayer("Actually, I know where to find this stuff.", animation = 588)
        }
    }
}

suspend fun cooksAssistantDialog(it: QueueTask)
{
    it.chatNpc("How are you getting on with finding the ingredients?", animation = 567)
    if (it.player.attr[cooksAssistantEgg] == false && it.player.inventory.contains(Items.EGG))
    {
        it.player.inventory.remove(Items.EGG)
        it.player.attr[cooksAssistantEgg] = true
        it.chatPlayer("Here's a fresh egg.", animation = 588)
    }
    if (it.player.attr[cooksAssistantMilk] ==false && it.player.inventory.contains(Items.BUCKET_OF_MILK))
    {
        it.player.inventory.remove(Items.BUCKET_OF_MILK)
        it.player.attr[cooksAssistantMilk] = true
        it.chatPlayer("Here's a bucket of milk.", animation = 588)
    }
    if (it.player.attr[cooksAssistantFlour] ==false && it.player.inventory.contains(Items.POT_OF_FLOUR))
    {
        it.player.inventory.remove(Items.POT_OF_FLOUR)
        it.player.attr[cooksAssistantFlour] = true
        it.chatPlayer("Here's a pot of flour.", animation = 588)
    }

    if (it.player.attr[cooksAssistantEgg] == true &&
            it.player.attr[cooksAssistantMilk] == true &&
            it.player.attr[cooksAssistantFlour] == true)
    {
        it.chatNpc("You've brought me everything I need! I am saved! Thank you!", animation = 567)
        it.chatPlayer("So do I get to go to the Duke's Party?", animation = 588)
        it.chatNpc("I'm afraid not, only the big cheeses get to dine with the Duke.", animation = 567)
        it.chatPlayer("Well, maybe one day I'll be important enough to sit on the Duke's table.", animation = 588)
        it.chatNpc("Maybe, but I won't be holding my breath.", animation = 567)
        it.player.attr[cooksAssistantProgress] = 1
        it.player.attr.remove(cooksAssistantEgg)
        it.player.attr.remove(cooksAssistantMilk)
        it.player.attr.remove(cooksAssistantFlour)
        return
    }

    var dynamicChat = if (it.player.attr[cooksAssistantEgg] == false && it.player.attr[cooksAssistantMilk] == false  && it.player.attr[cooksAssistantFlour] == false)
    {
        it.chatPlayer("I haven't got any of them yet, I'm still looking.", animation = 588)
        "Please get the ingredients quickly. I'm running out of time! The Duke will throw me into the streets!"
    }
    else
        "Thanks for the ingredients you have so far, Please get the rest quickly. I'm running out of time! The Duke will throw me into the streets!"

    it.chatNpc(dynamicChat, animation = 567)

    var dynamicMessage = "You still need to get:\n"
    if (it.player.attr[cooksAssistantMilk] ==false)
        dynamicMessage = "$dynamicMessage A pot of flour."
    if (it.player.attr[cooksAssistantFlour] ==false)
        dynamicMessage = "$dynamicMessage A bucket of milk."
    if (it.player.attr[cooksAssistantEgg] ==false)
        dynamicMessage = "$dynamicMessage An egg."
    it.messageBox(dynamicMessage)
    when (it.options("I'll get right on it.", "Can you remind me how to find these things again?")) {
        1 -> it.chatPlayer("I'll get right on it.", animation = 588)
        2 -> helpFindIngredients(it)
    }
}
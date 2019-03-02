package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random


class RandomPersonPhotoHandler(private val sender: AbsSender) {

    fun process(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  random person photo")

        val randomPhotoLink = "https://thispersondoesnotexist.com/image?seed="

        sender.execute(
            SendPhoto()
                .setChatId(update.message.chatId)
                .setPhoto(randomPhotoLink+ Random.nextLong())
        )

        return null
    }
}

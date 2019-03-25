package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random

class RandomPersonPhotoHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  random person photo")

        val randomPhotoLink = "https://thispersondoesnotexist.com/image?seed="

        sender.execute(
            SendPhoto()
                .setChatId(update.message.chatId)
                .setPhoto(randomPhotoLink+ Random.nextLong())
                .setReplyMarkup(keyboardMarkup)
        )

        return null
    }
}

package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

class KeyboardHandler(private val keyboardMarkup: ReplyKeyboardMarkup) : Handler {

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: keyboard")

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Here is keyboard")
            .setReplyMarkup(keyboardMarkup)
    }
}
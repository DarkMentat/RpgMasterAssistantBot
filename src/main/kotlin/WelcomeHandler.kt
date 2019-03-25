package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

class WelcomeHandler(private val keyboardMarkup: ReplyKeyboardMarkup) : Handler {

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: welcome")

        return SendMessage()
            .setChatId(update.message.chatId)
            .setReplyMarkup(keyboardMarkup)
            .setText("Welcome!")
    }
}
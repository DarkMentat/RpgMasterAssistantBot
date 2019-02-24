package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


class WelcomeHandler {

    fun process(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: welcome")

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Welcome!")
    }
}
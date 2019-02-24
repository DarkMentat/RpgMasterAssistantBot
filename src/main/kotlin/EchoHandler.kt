package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


class EchoHandler {

    fun process(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  echo")

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText(update.message.text)
    }
}
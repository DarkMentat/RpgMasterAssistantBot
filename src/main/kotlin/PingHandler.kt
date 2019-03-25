package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class PingHandler:Handler {

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  pong")

        val pong = if(update.message.text == "Ping") "Pong" else "pong"

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText(pong)
    }
}
package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender


class PingHandler(sender: AbsSender) : Handler(sender, "/ping") {

    override fun process(msg: String) {

        val pong = if (msg == "Ping") "Pong" else "pong"

        //todo keyboard?
        sender.execute(
            SendMessage.builder()
                .chatId(chatId.toString())
                .text(pong)
                .build()
        )
    }
}
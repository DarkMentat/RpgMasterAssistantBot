package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender


class WelcomeHandler(sender: AbsSender) : Handler(sender, "/welcome") {

    override fun process(msg: String) {

        //todo keyboard?
        sender.execute(
            SendMessage()
                .setChatId(chatId)
                .setText("Welcome!")
        )
    }
}
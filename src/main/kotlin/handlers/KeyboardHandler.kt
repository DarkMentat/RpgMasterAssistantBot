package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.bots.AbsSender


class KeyboardHandler(private val keyboardMarkup: ReplyKeyboardMarkup, sender: AbsSender) : Handler(sender, "/keyboard") {

    override fun process(msg: String) {
        sender.execute(
            SendMessage()
                .setChatId(chatId)
                .setText("Here is keyboard")
                .setReplyMarkup(keyboardMarkup)
        )
    }
}
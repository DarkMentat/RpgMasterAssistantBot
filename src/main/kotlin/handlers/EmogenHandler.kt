package org.darkmentat.handlers

import org.darkmentat.Resources.Emogen.emotions
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender


class EmogenHandler(sender: AbsSender) : Handler(sender, "/emogen") {

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/emogen_get_new")
            )
        )
    }

    override fun process(msg: String) {

        val first = emotions.keys.random()
        val second = emotions.getValue(first).keys.random()
        val third = emotions.getValue(first).getValue(second).random()

        sender.execute(
            SendMessage()
                .setChatId(chatId)
                .setText("$first → $second → $third")
                .setReplyMarkup(inlineUpdateButton)
        )
    }
}

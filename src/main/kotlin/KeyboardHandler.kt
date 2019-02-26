package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow


class KeyboardHandler {

    private val mainMenuKeyboard = arrayListOf(
        KeyboardRow().apply {
            add(KeyboardButton("Tarot 3 cards"))
            add("Emogen")
        },
        KeyboardRow().apply {
            add("Ping")
            add("(nothing)")
        }
    )

    fun process(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: keyboard")

        val keyboardMarkup = ReplyKeyboardMarkup().apply {
            keyboard = mainMenuKeyboard
        }

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Here is keyboard")
            .setReplyMarkup(keyboardMarkup)
    }
}
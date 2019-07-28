package org.darkmentat

import org.darkmentat.Resources.Emogen.emotions
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

class EmogenHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/emogen_get_new")

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/emogen_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  emogen")

        val first = emotions.keys.random()
        val second = emotions.getValue(first).keys.random()
        val third = emotions.getValue(first).getValue(second).random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("$first → $second → $third")
            .setReplyMarkup(inlineUpdateButton)
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/emogen_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            val first = emotions.keys.random()
            val second = emotions.getValue(first).keys.random()
            val third = emotions.getValue(first).getValue(second).random()

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("$first → $second → $third")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

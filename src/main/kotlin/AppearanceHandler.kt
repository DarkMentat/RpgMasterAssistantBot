package org.darkmentat

import org.darkmentat.Resources.Appearances.bodies
import org.darkmentat.Resources.Appearances.clothes
import org.darkmentat.Resources.Appearances.eyes
import org.darkmentat.Resources.Appearances.faces
import org.darkmentat.Resources.Appearances.genders
import org.darkmentat.Resources.Appearances.hair1
import org.darkmentat.Resources.Appearances.hair2
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

class AppearanceHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/appearance_get_new")

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/appearance_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  appearance")

        val gender = genders.random()
        val clothes = clothes.random()
        val body = bodies.random()
        val face = faces.random()
        val eyes = eyes.random()
        val hair = hair1.random().takeIf { it != "null" }?.plus(" ")?.plus(hair2.random()) ?: "Отсутствуют"

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Пол: $gender\nОдежда: $clothes\nТело: $body\nЛицо: $face\nГлаза: $eyes\nВолосы: $hair")
            .setReplyMarkup(inlineUpdateButton)
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/appearance_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            val gender = genders.random()
            val clothes = clothes.random()
            val body = bodies.random()
            val face = faces.random()
            val eyes = eyes.random()
            val hair = hair1.random().takeIf { it != "null" }?.plus(" ")?.plus(hair2.random()) ?: "Отсутствуют"

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("Пол: $gender\nОдежда: $clothes\nТело: $body\nЛицо: $face\nГлаза: $eyes\nВолосы: $hair")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

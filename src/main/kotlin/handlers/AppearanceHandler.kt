package org.darkmentat.handlers

import org.darkmentat.Resources.Appearances.bodies
import org.darkmentat.Resources.Appearances.clothes
import org.darkmentat.Resources.Appearances.eyes
import org.darkmentat.Resources.Appearances.faces
import org.darkmentat.Resources.Appearances.genders
import org.darkmentat.Resources.Appearances.hair1
import org.darkmentat.Resources.Appearances.hair2
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender


class AppearanceHandler(sender: AbsSender): Handler(sender, "/appearance") {

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton.builder().text("\uD83D\uDD01 Get New!").callbackData("/appearance_get_new").build()
            )
        )
    }

    override fun process(msg: String) {
        val gender = genders.random()
        val clothes = clothes.random()
        val body = bodies.random()
        val face = faces.random()
        val eyes = eyes.random()
        val hair = hair1.random().takeIf { it != "null" }?.plus(" ")?.plus(hair2.random()) ?: "Отсутствуют"

        sender.execute(
            SendMessage.builder()
            .chatId(chatId.toString())
            .text("Пол: $gender\nОдежда: $clothes\nТело: $body\nЛицо: $face\nГлаза: $eyes\nВолосы: $hair")
            .replyMarkup(inlineUpdateButton)
            .build()
        )
    }
}

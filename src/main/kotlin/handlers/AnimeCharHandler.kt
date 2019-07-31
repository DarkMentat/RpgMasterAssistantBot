package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import kotlin.random.Random


class AnimeCharHandler(sender: AbsSender): Handler(sender, "/anime_char") {

    private val baseUrl = "https://nyaa.shikimori.org/system/characters/original/"

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/anime_char_get_new")
            )
        )
    }

    override fun process(msg: String) {
        while (true) {
            try {
                sender.execute(
                    SendPhoto()
                        .setPhoto(baseUrl + Random.nextInt(1, 100000) + ".jpg")
                        .setChatId(chatId)
                        .setReplyMarkup(inlineUpdateButton)
                )
                break
            } catch (ex: TelegramApiRequestException) {}
        }
    }
}
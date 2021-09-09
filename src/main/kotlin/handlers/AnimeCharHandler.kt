package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
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
                InlineKeyboardButton.builder().text("\uD83D\uDD01 Get New!").callbackData("/anime_char_get_new").build()
            )
        )
    }

    override fun process(msg: String) {
        while (true) {
            try {
                sender.execute(
                    SendPhoto.builder()
                        .photo(InputFile(baseUrl + Random.nextInt(1, 100000) + ".jpg"))
                        .chatId(chatId.toString())
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
                break
            } catch (ex: TelegramApiRequestException) {}
        }
    }
}
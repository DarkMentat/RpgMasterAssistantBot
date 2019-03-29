package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import kotlin.random.Random

class AnimeCharHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/anime_char_get_new")

    private val myanimelistUrl = "https://myanimelist.net/character/"

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/anime_char_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: anime char")

        while (true) {
            try {
                sender.execute(
                    SendPhoto()
                        .setPhoto(myanimelistUrl + Random.nextInt(1, 100000))
                        .setChatId(update.message.chatId)
                        .setReplyMarkup(inlineUpdateButton)
                )
            }catch (ex: TelegramApiRequestException){}
            break
        }

        return null
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/anime_char_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            while (true) {
                try {
                    sender.execute(
                        SendPhoto()
                            .setChatId(callbackQuery.message.chatId)
                            .setPhoto(myanimelistUrl + Random.nextInt(1, 100000))
                            .setReplyMarkup(inlineUpdateButton)
                    )
                }catch (ex: TelegramApiRequestException){}
                break
            }
        }
    }
}
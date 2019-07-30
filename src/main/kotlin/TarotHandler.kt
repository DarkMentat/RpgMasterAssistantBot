package org.darkmentat

import org.darkmentat.Resources.Tarot.imagesNewVision
import org.darkmentat.Resources.Tarot.imagesRaider
import org.darkmentat.Resources.Tarot.imagesWitches
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import java.time.ZonedDateTime
import kotlin.random.Random

class TarotHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/tarot_get_new", "/tarot_get_new_witch")

    private val tarotImageBaseUrl = "https://i.ibb.co/"

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/tarot_get_new"),
                InlineKeyboardButton().setText("\uD83E\uDDD9\u200D♀ Witch!").setCallbackData("/tarot_get_new_witch")
            )
        )
    }

    private val random = Random(ZonedDateTime.now().toInstant().toEpochMilli())

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: tarot card")

        if(update.message.text == "/tarot_get_pair"){
            val album = SendMediaGroup()

            val index = random.nextInt(0, imagesRaider.size)

            album.media = listOf(
                InputMediaPhoto(tarotImageBaseUrl + imagesRaider[index], ""),
                InputMediaPhoto(tarotImageBaseUrl + imagesNewVision[index], "")
            )

            album.setChatId(update.message.chatId)

            sender.execute(album)
            return null
        }


        sender.execute(SendPhoto()
            .setPhoto(tarotImageBaseUrl + imagesNewVision[random.nextInt(imagesNewVision.size)])
            .setChatId(update.message.chatId)
            .setReplyMarkup(inlineUpdateButton)
        )

        return null
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/tarot_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            sender.execute(
                SendPhoto()
                    .setChatId(callbackQuery.message.chatId)
                    .setPhoto(tarotImageBaseUrl + imagesNewVision[random.nextInt(imagesNewVision.size)])
                    .setChatId(callbackQuery.message.chatId)
                    .setReplyMarkup(inlineUpdateButton)
            )
        }else if(callbackQuery.data == "/tarot_get_new_witch") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            sender.execute(
                SendPhoto()
                    .setChatId(callbackQuery.message.chatId)
                    .setPhoto(tarotImageBaseUrl + imagesWitches[random.nextInt(imagesWitches.size)])
                    .setChatId(callbackQuery.message.chatId)
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}
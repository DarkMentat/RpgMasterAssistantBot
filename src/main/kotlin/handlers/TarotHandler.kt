package org.darkmentat.handlers

import org.darkmentat.Resources.Tarot.imagesNewVision
import org.darkmentat.Resources.Tarot.imagesRaider
import org.darkmentat.Resources.Tarot.imagesWitches
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import java.time.ZonedDateTime
import kotlin.random.Random


class TarotHandler(sender: AbsSender) : Handler(sender, "/tarot") {

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

    override fun process(msg: String) {

        when(msg){
            "/tarot_get_pair" -> {
                val album = SendMediaGroup()

                val index = random.nextInt(0, imagesRaider.size)

                album.media = listOf(
                    InputMediaPhoto(tarotImageBaseUrl + imagesRaider[index], ""),
                    InputMediaPhoto(tarotImageBaseUrl + imagesNewVision[index], "")
                )

                album.setChatId(chatId)

                sender.execute(album)
            }

            "/tarot_get_new_witch" -> {
                sender.execute(
                    SendPhoto()
                        .setChatId(chatId)
                        .setPhoto(tarotImageBaseUrl + imagesWitches[random.nextInt(imagesWitches.size)])
                        .setReplyMarkup(inlineUpdateButton)
                )
            }

            else -> {
                sender.execute(
                    SendPhoto()
                        .setPhoto(tarotImageBaseUrl + imagesNewVision[random.nextInt(imagesNewVision.size)])
                        .setChatId(chatId)
                        .setReplyMarkup(inlineUpdateButton)
                )
            }
        }
    }
}
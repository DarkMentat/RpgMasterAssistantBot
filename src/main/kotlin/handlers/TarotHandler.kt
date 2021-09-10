package org.darkmentat.handlers

import org.darkmentat.Resources.Tarot.imagesBlackGrimoire
import org.darkmentat.Resources.Tarot.imagesNewVision
import org.darkmentat.Resources.Tarot.imagesRaider
import org.darkmentat.Resources.Tarot.imagesRitualAbuse
import org.darkmentat.Resources.Tarot.imagesWitches
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
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
                InlineKeyboardButton.builder().text("\uD83D\uDD01 New!").callbackData("/tarot_get_new").build(),
                InlineKeyboardButton.builder().text("\uD83E\uDDD9\u200D♀ Witch!").callbackData("/tarot_get_new_witch").build(),
                InlineKeyboardButton.builder().text("\uD83D\uDC80 Abuse!").callbackData("/tarot_get_ritual_abuse").build(),
                InlineKeyboardButton.builder().text("\uD83E\uDD91 Cthulhu!").callbackData("/tarot_get_black_grimoire").build()
            )
        )
    }

    private val random = Random(ZonedDateTime.now().toInstant().toEpochMilli())

    override fun process(msg: String) {

        when(msg){
            "/tarot_get_pair" -> {
                val index = random.nextInt(0, imagesRaider.size)

                sender.execute(
                    SendMediaGroup.builder()
                        .chatId(chatId.toString())
                        .medias(
                            listOf(
                                InputMediaPhoto(tarotImageBaseUrl + imagesRaider[index]),
                                InputMediaPhoto(tarotImageBaseUrl + imagesNewVision[index])
                            )
                        )
                        .build()
                )
            }

            "/tarot_get_classic" -> {
                sender.execute(
                    SendPhoto.builder()
                        .chatId(chatId.toString())
                        .photo(InputFile(tarotImageBaseUrl + imagesRaider[random.nextInt(imagesRaider.size)]))
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
            }

            "/tarot_get_new_witch" -> {
                sender.execute(
                    SendPhoto.builder()
                        .chatId(chatId.toString())
                        .photo(InputFile(tarotImageBaseUrl + imagesWitches[random.nextInt(imagesWitches.size)]))
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
            }

            "/tarot_get_ritual_abuse" -> {
                sender.execute(
                    SendPhoto.builder()
                        .chatId(chatId.toString())
                        .photo(InputFile(tarotImageBaseUrl + imagesRitualAbuse[random.nextInt(imagesRitualAbuse.size)]))
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
            }

            "/tarot_get_black_grimoire" -> {
                sender.execute(
                    SendPhoto.builder()
                        .chatId(chatId.toString())
                        .photo(InputFile(tarotImageBaseUrl + imagesBlackGrimoire[random.nextInt(imagesBlackGrimoire.size)]))
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
            }
            else -> {
                sender.execute(
                    SendPhoto.builder()
                        .photo(InputFile(tarotImageBaseUrl + imagesNewVision[random.nextInt(imagesNewVision.size)]))
                        .chatId(chatId.toString())
                        .replyMarkup(inlineUpdateButton)
                        .build()
                )
            }
        }
    }
}
package org.darkmentat.handlers

import org.darkmentat.Resources.Names.chineseFem
import org.darkmentat.Resources.Names.chineseMale
import org.darkmentat.Resources.Names.englishFem
import org.darkmentat.Resources.Names.englishMale
import org.darkmentat.Resources.Names.exoticFem
import org.darkmentat.Resources.Names.exoticMale
import org.darkmentat.Resources.Names.frenchFem
import org.darkmentat.Resources.Names.frenchMale
import org.darkmentat.Resources.Names.germanFem
import org.darkmentat.Resources.Names.germanMale
import org.darkmentat.Resources.Names.japaneseFem
import org.darkmentat.Resources.Names.japaneseMale
import org.darkmentat.Resources.Names.nicknames
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random


class NameGeneratorHandler(sender: AbsSender) : Handler(sender, "/random_name") {

    private val inlineReplyKeyboard = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton.builder().text("\uD83D\uDD01").callbackData("/random_name").build(),
                InlineKeyboardButton.builder().text("\uD83C\uDDEC\uD83C\uDDE7").callbackData("/random_name_eng").build(),
                InlineKeyboardButton.builder().text("\uD83C\uDDEB\uD83C\uDDF7").callbackData("/random_name_fra").build(),
                InlineKeyboardButton.builder().text("\uD83C\uDDE9\uD83C\uDDEA").callbackData("/random_name_ger").build(),
                InlineKeyboardButton.builder().text("\uD83C\uDDEF\uD83C\uDDF5").callbackData("/random_name_jap").build(),
                InlineKeyboardButton.builder().text("\uD83C\uDDE8\uD83C\uDDF3").callbackData("/random_name_chi").build(),
                InlineKeyboardButton.builder().text("\uD83D\uDDFB").callbackData("/random_name_exotic").build(),
                InlineKeyboardButton.builder().text("\uD83D\uDC3A").callbackData("/random_name_nickname").build()
            )
        )
    }

    override fun process(msg: String) {

        val name = when(msg){

            "/random_name" -> randomName()
            "/random_name_eng" -> if(Random.nextBoolean()) englishMale.random() else englishFem.random()
            "/random_name_fra" -> if(Random.nextBoolean()) frenchMale.random() else frenchFem.random()
            "/random_name_ger" -> if(Random.nextBoolean()) germanMale.random() else germanFem.random()
            "/random_name_jap" -> if(Random.nextBoolean()) japaneseMale.random() else japaneseFem.random()
            "/random_name_chi" -> if(Random.nextBoolean()) chineseMale.random() else chineseFem.random()
            "/random_name_exotic" -> if(Random.nextBoolean()) exoticMale.random() else exoticFem.random()
            "/random_name_nickname" -> nicknames.random()

            else -> return
        }

        sender.execute(
            SendMessage.builder()
                .chatId(chatId.toString())
                .text(name)
                .replyMarkup(inlineReplyKeyboard)
                .build()
        )
    }

    private fun randomName(): String{
        val d100 = Random.nextInt(100)

        return when{
            d100 < 25 -> englishMale.random()
            d100 < 50 -> englishFem.random()
            d100 < 55 -> frenchMale.random()
            d100 < 60 -> frenchFem.random()
            d100 < 65 -> germanMale.random()
            d100 < 70 -> germanFem.random()
            d100 < 75 -> japaneseMale.random()
            d100 < 80 -> japaneseFem.random()
            d100 < 85 -> chineseMale.random()
            d100 < 90 -> chineseFem.random()
            d100 < 95 -> exoticMale.random()
            else -> exoticFem.random()
        }
    }
}

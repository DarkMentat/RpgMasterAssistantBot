package org.darkmentat.handlers

import org.darkmentat.Resources.Natures.fears
import org.darkmentat.Resources.Natures.manners
import org.darkmentat.Resources.Natures.motives
import org.darkmentat.Resources.Natures.natures
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender


class NatureHandler(sender: AbsSender) : Handler(sender, "/nature") {

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/nature_get_new")
            )
        )
    }

    override fun process(msg: String) {

            val nature = natures.random()
            val demeanor = natures.random()
            val motive = motives.random()
            val manners = manners.random()
            val fear = fears.random()

            sender.execute(
                SendMessage()
                    .setChatId(chatId)
                    .setText("Натура: $nature\nМаска: $demeanor\nПовадки: $manners\nМотив: $motive\nБоится: $fear")
                    .setReplyMarkup(inlineUpdateButton)
            )
    }
}

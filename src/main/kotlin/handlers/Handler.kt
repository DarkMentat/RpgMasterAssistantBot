package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.bots.AbsSender

abstract class Handler(
    val sender: AbsSender,
    val callPrefix: String) {

    var chatId: Long = 0

    fun processCallback(callbackQuery: CallbackQuery, cmd: String){

        chatId = callbackQuery.message.chatId

        sender.execute(
            EditMessageReplyMarkup()
                .setChatId(callbackQuery.message.chatId)
                .setMessageId(callbackQuery.message.messageId)
                .setReplyMarkup(InlineKeyboardMarkup())
        )

        process(cmd)
    }

    fun processDirect(update: Update, cmd: String){

        chatId = update.message.chatId

        process(cmd)
    }

    abstract fun process(msg: String)
}
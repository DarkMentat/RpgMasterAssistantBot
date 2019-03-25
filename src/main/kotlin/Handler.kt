package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update

interface Handler {

    val callbackCommands: List<String>
        get() = emptyList()

    fun processCallback(callbackQuery: CallbackQuery) = Unit
    fun processDirect(update: Update): BotApiMethod<out BotApiObject>?
}
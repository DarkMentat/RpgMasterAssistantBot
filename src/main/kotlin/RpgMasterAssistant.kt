package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender

class RpgMasterAssistant(sender: AbsSender){

    private val mainMenuKeyboard = arrayListOf(
        KeyboardRow().apply {
            add(KeyboardButton("Get Tarot Card"))
            add("Emogen")
        },
        KeyboardRow().apply {
            add("Ping")
            add("(nothing)")
        },
        KeyboardRow().apply {
            add("Random name")
        }
    )

    private val keyboardMarkup = ReplyKeyboardMarkup().apply {
        keyboard = mainMenuKeyboard
        resizeKeyboard = true
        oneTimeKeyboard = false
    }

    private val nameGeneratorHandler = NameGeneratorHandler(sender, keyboardMarkup)
    private val pingHandler = PingHandler(keyboardMarkup)
    private val welcomeHandler = WelcomeHandler(keyboardMarkup)
    private val tarotHandler = TarotHandler(sender, keyboardMarkup)
    private val emogenHandler = EmogenHandler(sender, keyboardMarkup)

    private val handlers = listOf(nameGeneratorHandler, pingHandler, welcomeHandler, tarotHandler, emogenHandler)

    fun onUpdate(update: Update?): BotApiMethod<out BotApiObject>? {
        if(update?.hasCallbackQuery() == true){

            println("RECV: callback " + update.callbackQuery.data)

            for (handler in handlers){
                if(handler.callbackCommands.contains(update.callbackQuery.data)){
                    handler.processCallback(update.callbackQuery)
                }
            }
        }

        val msg = update?.message ?: return null
        val txt = msg.text ?: return null

        println("RECV: $txt")

        return when(txt){

            "/start" -> welcomeHandler.processDirect(update)

            "/ping" -> pingHandler.processDirect(update)
            "ping" -> pingHandler.processDirect(update)
            "Ping" -> pingHandler.processDirect(update)

            "Get Tarot Card" -> tarotHandler.processDirect(update)
            "/tarot" -> tarotHandler.processDirect(update)

            "Emogen" -> emogenHandler.processDirect(update)

            "/random_name" -> nameGeneratorHandler.processDirect(update)
            "Random name" -> nameGeneratorHandler.processDirect(update)

            else -> null
        }
    }
}
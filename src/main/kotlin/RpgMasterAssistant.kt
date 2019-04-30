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
            add("Appearance")
            add("Nature/Demeanor")
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
    private val natureHandler = NatureHandler(sender, keyboardMarkup)
    private val keyboardHandler = KeyboardHandler(keyboardMarkup)
    private val animeCharHandler = AnimeCharHandler(sender, keyboardMarkup)
    private val appearanceHandler = AppearanceHandler(sender, keyboardMarkup)
    private val lotfpSummonHandler = LotfpSummonHandler(sender)

    private val handlers = listOf(nameGeneratorHandler, pingHandler, welcomeHandler, tarotHandler, emogenHandler, natureHandler, keyboardHandler, animeCharHandler, appearanceHandler, lotfpSummonHandler)

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

        if(txt.startsWith("/summon_12_hd") || txt.startsWith("/summon_20_hd")){
            return lotfpSummonHandler.processDirect(update)
        }

        return when(txt){

            "/start" -> welcomeHandler.processDirect(update)

            "/ping" -> pingHandler.processDirect(update)
            "ping" -> pingHandler.processDirect(update)
            "Ping" -> pingHandler.processDirect(update)

            "Get Tarot Card" -> tarotHandler.processDirect(update)
            "/tarot" -> tarotHandler.processDirect(update)
            "/tarot_get_pair" -> tarotHandler.processDirect(update)

            "Emogen" -> emogenHandler.processDirect(update)
            "/emogen" -> emogenHandler.processDirect(update)

            "Nature/Demeanor" -> natureHandler.processDirect(update)
            "/nature" -> natureHandler.processDirect(update)

            "/random_name" -> nameGeneratorHandler.processDirect(update)
            "Random name" -> nameGeneratorHandler.processDirect(update)

            "/keyboard" -> keyboardHandler.processDirect(update)

            "/anime_char" -> animeCharHandler.processDirect(update)

            "Appearance" -> appearanceHandler.processDirect(update)
            "/appearance" -> appearanceHandler.processDirect(update)

            else -> null
        }
    }
}
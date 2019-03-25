package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class RpgMasterAssistant(sender: AbsSender){

    private val echoHandler = EchoHandler()
    private val nameGeneratorHandler = NameGeneratorHandler(sender)
    private val keyboardHandler = KeyboardHandler()
    private val pingHandler = PingHandler()
    private val welcomeHandler = WelcomeHandler()
    private val tarotHandler = TarotHandler(sender)
    private val randomPersonPhotoHandler= RandomPersonPhotoHandler(sender)
    private val emogenHandler = EmogenHandler()

    private val handlers = listOf(echoHandler, nameGeneratorHandler, keyboardHandler, pingHandler, welcomeHandler, tarotHandler, randomPersonPhotoHandler, emogenHandler)

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

            "/keyboard" -> keyboardHandler.processDirect(update)

            "/ping" -> pingHandler.processDirect(update)
            "ping" -> pingHandler.processDirect(update)
            "Ping" -> pingHandler.processDirect(update)

            "Tarot 3 cards" -> tarotHandler.processDirect(update)

            "Random person photo" -> randomPersonPhotoHandler.processDirect(update)
            "/random_person_photo" -> randomPersonPhotoHandler.processDirect(update)

            "Emogen" -> emogenHandler.processDirect(update)

            "/random_name" -> nameGeneratorHandler.processDirect(update)
            "Random name" -> nameGeneratorHandler.processDirect(update)

            "(nothing)" -> null

            else -> echoHandler.processDirect(update)
        }
    }
}
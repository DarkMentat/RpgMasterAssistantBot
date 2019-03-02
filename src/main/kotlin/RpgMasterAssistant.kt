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

    fun onUpdate(update: Update?): BotApiMethod<out BotApiObject>? {
        if(update?.hasCallbackQuery() == true){

            println("RECV: callback " + update.callbackQuery.data)

            when(update.callbackQuery.data){

                "/random_name" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_eng" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_fra" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_ger" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_jap" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_chi" -> nameGeneratorHandler.processCallback(update.callbackQuery)
                "/random_name_exotic" -> nameGeneratorHandler.processCallback(update.callbackQuery)
            }
        }

        val msg = update?.message ?: return null
        val txt = msg.text ?: return null

        println("RECV: $txt")

        return when(txt){

            "/start" -> welcomeHandler.process(update)

            "/keyboard" -> keyboardHandler.process(update)

            "/ping" -> pingHandler.process(update)
            "ping" -> pingHandler.process(update)
            "Ping" -> pingHandler.process(update)

            "Tarot 3 cards" -> tarotHandler.process(update)

            "Random person photo" -> randomPersonPhotoHandler.process(update)
            "/random_person_photo" -> randomPersonPhotoHandler.process(update)

            "Emogen" -> emogenHandler.process(update)

            "/random_name" -> nameGeneratorHandler.process(update)
            "Random name" -> nameGeneratorHandler.process(update)

            "(nothing)" -> null

            else -> echoHandler.process(update)
        }
    }
}
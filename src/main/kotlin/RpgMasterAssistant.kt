package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender


class RpgMasterAssistant(sender: AbsSender){

    private val echoHandler = EchoHandler()
    private val keyboardHandler = KeyboardHandler()
    private val pingHandler = PingHandler()
    private val welcomeHandler = WelcomeHandler()
    private val tarotHandler = TarotHandler(sender)

    fun onUpdate(update: Update?): BotApiMethod<out BotApiObject>? {

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

            "(nothing)" -> null

            else -> echoHandler.process(update)
        }
    }
}
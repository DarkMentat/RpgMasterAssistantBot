package org.darkmentat

import org.darkmentat.handlers.*
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender


class RpgMasterAssistant(sender: AbsSender){

    private val mainMenuKeyboard = MainMenuKeyboard()

    private val handlers = listOf(
        NameGeneratorHandler(sender),
        PingHandler(sender),
        WelcomeHandler(sender),
        TarotHandler(sender),
        EmogenHandler(sender),
        NatureHandler(sender),
        KeyboardHandler(mainMenuKeyboard, sender),
        AnimeCharHandler(sender),
        AppearanceHandler(sender),
        LotfpSummonHandler(sender),
        AugmentedRealityCyberpunkCityHandler(sender)
    )

    fun onUpdate(update: Update?): BotApiMethod<out BotApiObject>? {

        if(update == null)
            return null

        val msg = update.callbackQuery?.data ?: update.message?.text ?: return null
        val cmd = mainMenuKeyboard.textToCommand(msg)

        for (handler in handlers) {
            if (cmd.startsWith(handler.callPrefix)) {
                if(update.hasCallbackQuery()){
                    println("RECV: callback $cmd")
                    handler.processCallback(update.callbackQuery, cmd)
                }else{
                    println("RECV: $cmd")
                    handler.processDirect(update, cmd)
                }
                break
            }
        }

        return null
    }
}
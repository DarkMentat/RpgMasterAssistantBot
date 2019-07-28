package org.darkmentat

import org.darkmentat.Resources.Cyberpunk.buildingFeatures
import org.darkmentat.Resources.Cyberpunk.downtown
import org.darkmentat.Resources.Cyberpunk.smells
import org.darkmentat.Resources.Cyberpunk.sounds
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

class AugmentedRealityCyberpunkCityHandler(
    private val sender: AbsSender
): Handler {

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: cyberpunk downtown")

        val msg = update.message?.text?:return null

        val res = "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                   "Smells like ${smells.random()} and ${smells.random()}, Sound ${sounds.random()} and ${sounds.random()}"

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText(res)
            .enableMarkdown(true)
    }
}
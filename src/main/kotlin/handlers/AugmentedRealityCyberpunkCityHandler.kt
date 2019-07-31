package org.darkmentat.handlers

import org.darkmentat.Resources.Cyberpunk.buildingFeatures
import org.darkmentat.Resources.Cyberpunk.downtown
import org.darkmentat.Resources.Cyberpunk.smells
import org.darkmentat.Resources.Cyberpunk.sounds
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender


class AugmentedRealityCyberpunkCityHandler(sender: AbsSender) : Handler(sender, "/ar") {

    //todo inline keyboard

    override fun process(msg: String) {

        val res = "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                  "${downtown.random()}\n_${buildingFeatures.random()}_\n_${buildingFeatures.random()}_\n\n" +
                   "Smells like ${smells.random()} and ${smells.random()}, Sound ${sounds.random()} and ${sounds.random()}"

        sender.execute(
            SendMessage()
                .setChatId(chatId)
                .setText(res)
                .enableMarkdown(true)
        )
    }
}
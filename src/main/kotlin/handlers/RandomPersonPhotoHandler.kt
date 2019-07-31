package org.darkmentat.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random


class RandomPersonPhotoHandler(sender: AbsSender) : Handler(sender, "/random_photo") {

    override fun process(msg: String) {

        val randomPhotoLink = "https://thispersondoesnotexist.com/image?seed="

        //todo keyboard?
        sender.execute(
            SendPhoto()
                .setChatId(chatId)
                .setPhoto(randomPhotoLink+ Random.nextLong())
        )
    }
}

package org.darkmentat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(RpgMasterAssistantBot())
}

class RpgMasterAssistantBot : TelegramLongPollingBot() {

    override fun getBotToken() = System.getenv("BOT_TOKEN")
    override fun getBotUsername() = System.getenv("BOT_NAME")

    override fun onUpdateReceived(update: Update?) {

        val msg = update?.message ?: return
        val txt = update.message?.text ?: return

        println("ECHO: $txt")

        execute(
            SendMessage()
                .setChatId(msg.chatId)
                .setText(txt)
                .enableMarkdown(true)
        )
    }
}
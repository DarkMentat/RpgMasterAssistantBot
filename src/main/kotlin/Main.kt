package org.darkmentat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import kotlin.random.Random
import org.telegram.telegrambots.meta.logging.BotLogger
import java.io.Serializable
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord


fun main(args: Array<String>) {

    ApiContextInitializer.init()

    val externalUrl = System.getenv("APP_URL")
    val internalUrl = "https://0.0.0.0:" + System.getenv("PORT")

    if(externalUrl != null){
        //Using webhooks

        println("external url: $externalUrl")
        println("internal url: $internalUrl")

        val api = TelegramBotsApi(externalUrl, internalUrl)

        api.registerBot(RpgMasterAssistantWebhookBot())

    }else{
        //Using long polling

        TelegramBotsApi().registerBot(RpgMasterAssistantLongPollingBot())
    }
}


class RpgMasterAssistantWebhookBot : TelegramWebhookBot() {

    private val assistant = RpgMasterAssistant(this)

    override fun getBotPath() = System.getenv("BOT_PATH")
    override fun getBotToken() = System.getenv("BOT_TOKEN")
    override fun getBotUsername() = System.getenv("BOT_NAME")

    override fun onWebhookUpdateReceived(update: Update?) = assistant.onUpdate(update)
}

class RpgMasterAssistantLongPollingBot : TelegramLongPollingBot() {

    private val assistant = RpgMasterAssistant(this)

    override fun getBotToken() = System.getenv("BOT_TOKEN")
    override fun getBotUsername() = System.getenv("BOT_NAME")

    @Suppress("UNCHECKED_CAST")
    override fun onUpdateReceived(update: Update?) {

        val msg = assistant.onUpdate(update) as BotApiMethod<BotApiObject>?

        if(msg != null)
            execute(msg)
    }
}
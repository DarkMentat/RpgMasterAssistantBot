package org.darkmentat

import org.apache.log4j.BasicConfigurator
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook
import java.io.File


fun main(args: Array<String>) {

    BasicConfigurator.configure();

    val externalUrl = System.getenv("APP_URL")
    val ip = System.getenv("HOST_IP")
    val internalUrl = "https://0.0.0.0:" + System.getenv("PORT")
    val keystorePass = System.getenv("KEYSTORE_PASS")

    if(externalUrl != null){
        //Using webhooks

        println("external url: $externalUrl")
        println("internal url: $internalUrl")

        val defaultWebhookInstance = DefaultWebhook()
        defaultWebhookInstance.setInternalUrl(internalUrl)
        defaultWebhookInstance.setKeyStore("keystore.jks", keystorePass);
        val api = TelegramBotsApi(DefaultBotSession::class.java, defaultWebhookInstance)
        val bot = RpgMasterAssistantWebhookBot()
        api.registerBot(bot, SetWebhook.builder().url(externalUrl).ipAddress(ip).certificate(InputFile(File("public_cert.pem"))).build())

    }else{
        //Using long polling

        TelegramBotsApi(DefaultBotSession::class.java).registerBot(RpgMasterAssistantLongPollingBot())
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
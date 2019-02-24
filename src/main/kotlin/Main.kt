package org.darkmentat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import kotlin.random.Random
import java.util.logging.ConsoleHandler
import org.telegram.telegrambots.meta.logging.BotLogger
import java.util.logging.Level


fun main(args: Array<String>) {

    val pathToCertificatePublicKey = "./rpg-master-assistant-bot.pem"
    val pathToCertificateStore = "./rpg-master-assistant-bot.jks"
    val certificateStorePassword = "rpg-master-assistant-bot"

    BotLogger.setLevel(Level.ALL)
    BotLogger.registerLogger(ConsoleHandler())

    ApiContextInitializer.init()

    val api = TelegramBotsApi(
        pathToCertificateStore,
        certificateStorePassword,
        System.getenv("APP_URL"),
        "https://0.0.0.0:" + if (System.getenv("PORT") != null) System.getenv("PORT") else "33500",
        pathToCertificatePublicKey
    )

    api.registerBot(RpgMasterAssistantBot())
}


class RpgMasterAssistantBot : TelegramWebhookBot() {

    override fun getBotPath() = System.getenv("BOT_PATH")
    override fun getBotToken() = System.getenv("BOT_TOKEN")
    override fun getBotUsername() = System.getenv("BOT_NAME")

    private val tarotImageBaseUrl = "https://ibb.co/"
    private val tarotImages = listOf(
        "JtXPK6X", "z2SVLgw", "zHsGN24", "6m9qR9f", "sbwyDw1", "jL0jLZn", "1dGwDXP", "3MssLYC", "Yd6hMh3", "m6HwXr0",
        "16vDbXM", "sWTmnvd", "SxjV6Bp", "1QT8KKG", "r0LjcQ6", "DV8jbtH", "zsgzVXh", "8BgR4JK", "W333C5F", "MCb12LY",
        "ckNXvd6", "xjGvNFC", "YjcgQft", "p4s7Sck", "hDqnxDf", "gZcfBp1", "Xp6gw2r", "vVLRbQg", "BK1Rk53", "bQkhLKW",
        "C8C3kBs", "sgPQCBG", "5K2sb1r", "BngGWHs", "GcgWmny", "hy9Ng1p", "hLBZ3qn", "jMDr6zj", "jhgPJLz", "gDnqgmT",
        "60JbXXR", "XYyHYHN", "ZKNY8K1", "Vmr5pJ2", "G3LyKJJ", "G7F0b7Y", "85yx9cv", "1RZC318", "8mWNZCn", "2sbVrVp",
        "PDSP6wK", "XVFTzSY", "17w4Hwr", "fFr6zpd", "D7ys1jY", "8PXcXBt", "pJvLZvv", "qYR7TDy", "zVwh9Wb", "HnSR7Hb",
        "KXg65v9", "2n4N3Bw", "jwt7Z4w", "KXjD65t", "Cs5HcVw", "gSV9nf4", "bmWp0hG", "Jz1bRLn", "XYrxRQZ", "cLb9B2g",
        "nCTQKpv", "4JrdB5m", "MVbsxV5", "hZ6nKWd", "SXSJcLg", "hYTD41s", "6XSS66B", "rHzwSXR"
    )

    private val mainMenuKeyboard = arrayListOf(
        KeyboardRow().apply {
            add(KeyboardButton("Tarot 3 cards"))
            add("(nothing)")
        },
        KeyboardRow().apply {
            add("Ping")
            add("(nothing)")
        }
    )

    override fun onWebhookUpdateReceived(update: Update?): BotApiMethod<*>? {

        val msg = update?.message ?: return null
        val txt = msg.text ?: return null

        println("RECV: $txt")

        when(txt){
            "/keyboard" -> {

                println("SEND: keyboard")

                val keyboardMarkup = ReplyKeyboardMarkup().apply {
                    keyboard = mainMenuKeyboard
                }

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Here is keyboard")
                        .setReplyMarkup(keyboardMarkup)
            }

            "Ping" -> {
                println("SEND: ping pong")

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Pong")
            }

            "ping" -> {
                println("SEND: ping pong")

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText("pong")
            }

            "/start" -> {
                println("SEND: welcome")

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Welcome!")
            }

            "/ping" -> {
                println("SEND: ping pong")

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText("pong")
            }

            "Tarot 3 cards" -> {
                println("SEND: 3 tarot photos")

                val album = SendMediaGroup()

                val i1 = Random.nextInt(tarotImages.size)
                var i2 = Random.nextInt(tarotImages.size)
                var i3 = Random.nextInt(tarotImages.size)

                while (i1 == i2 || i1 == i3 || i2 == i3){
                    i2 = Random.nextInt(tarotImages.size)
                    i3 = Random.nextInt(tarotImages.size)
                }

                album.media = listOf(
                    InputMediaPhoto(tarotImageBaseUrl + tarotImages[i1], ""),
                    InputMediaPhoto(tarotImageBaseUrl + tarotImages[i2], ""),
                    InputMediaPhoto(tarotImageBaseUrl + tarotImages[i3], "")
                )

                execute(album.setChatId(msg.chatId))

                return null
            }

            "(nothing)" -> {}

            else -> {

                println("SEND: echo \"$txt\"")

                return SendMessage()
                        .setChatId(msg.chatId)
                        .setText(txt)
            }
        }

        return null
    }
}
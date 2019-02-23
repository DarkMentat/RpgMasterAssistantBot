package org.darkmentat

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow


fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(RpgMasterAssistantBot())
}

class RpgMasterAssistantBot : TelegramLongPollingBot() {

    override fun getBotToken() = System.getenv("BOT_TOKEN")
    override fun getBotUsername() = System.getenv("BOT_NAME")

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

    override fun onUpdateReceived(update: Update?) {

        val msg = update?.message ?: return
        val txt = msg.text ?: return

        println("RECV: $txt")

        when(txt){
            "/keyboard" -> {

                println("SEND: keyboard")

                val keyboardMarkup = ReplyKeyboardMarkup().apply {
                    keyboard = mainMenuKeyboard
                }

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Here is keyboard")
                        .setReplyMarkup(keyboardMarkup)
                )
            }

            "Ping" -> {
                println("SEND: ping pong")

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Pong")
                )
            }

            "ping" -> {
                println("SEND: ping pong")

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText("pong")
                )
            }

            "/start" -> {
                println("SEND: welcome")

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText("Welcome!")
                )
            }

            "/ping" -> {
                println("SEND: ping pong")

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText("pong")
                )
            }

            "Tarot 3 cards" -> {}
            "(nothing)" -> {}

            else -> {

                println("SEND: echo \"$txt\"")

                execute(
                    SendMessage()
                        .setChatId(msg.chatId)
                        .setText(txt)
                )
            }
        }
    }
}
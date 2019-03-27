package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

class NatureHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/nature_get_new")

    val natures = listOf(
        "Архитектор", "Автократ", "Бон Вивант", "Браво", "Опекун", "Проповедник", "Ребенок", "Победитель", "Конформист", "Ловкач", "Брюзга",
        "Асоциал", "Директор", "Фанатик", "Щеголь", "Судья", "Одиночка", "Мученик", "Мазохист", "Монстр", "Педагог", "Кающийся грешник", "Перфекционист",
        "Бунтарь", "Плут", "Мастер выживания", "Авантюрист", "Традиционалист", "Шутник", "Мечтатель", "Идеалист", "Солдат", "Дилетант", "Естествоиспытатель",
        "Капиталист", "Хамелеон", "Калейдоскоп ужасов", "Загадка", "Глаз бури", "Гуру", "Садист", "Социопат", "Артист", "Трус", "Исследователь",
        "Фаталист", "Футурист", "Чужак", "Вопрошающий", "Варвар", "Защитник", "Новатор", "Сорвиголова"
    )

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/nature_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  emogen")

        val nature = natures.random()
        val demeanor = natures.random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Натура: $nature\nМаска: $demeanor")
            .setReplyMarkup(inlineUpdateButton)
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/nature_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )


            val nature = natures.random()
            val demeanor = natures.random()

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("Натура: $nature\nМаска: $demeanor")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

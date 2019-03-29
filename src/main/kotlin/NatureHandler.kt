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

    private val natures = listOf(
        "Архитектор", "Автократ", "Бон Вивант", "Браво", "Опекун", "Проповедник", "Ребенок", "Победитель", "Конформист", "Ловкач", "Брюзга",
        "Асоциал", "Директор", "Фанатик", "Щеголь", "Судья", "Одиночка", "Мученик", "Мазохист", "Монстр", "Педагог", "Кающийся грешник", "Перфекционист",
        "Бунтарь", "Плут", "Мастер выживания", "Авантюрист", "Традиционалист", "Шутник", "Мечтатель", "Идеалист", "Солдат", "Дилетант", "Естествоиспытатель",
        "Капиталист", "Хамелеон", "Калейдоскоп ужасов", "Загадка", "Глаз бури", "Гуру", "Садист", "Социопат", "Артист", "Трус", "Исследователь",
        "Фаталист", "Футурист", "Чужак", "Вопрошающий", "Варвар", "Защитник", "Новатор", "Сорвиголова"
    )

    private val motives = listOf(
        "Получить больше власти/денег/влияния", "Создать себе имя", "Отомстить за брата/сестру/родителей/возлюбленных", "Разрушить систему",
        "Защитить независимое место или заведение", "Получить кровь/наркотики", "Заняться сексом", "Найти способ прекратить этот кошмар",
        "Упиваться ненужной роскошью", "Защитить свою репутацию", "Добиться справедливости для обиженных", "Дать/предложить прощение виновным",
        "Выяснить правду о ком-то", "Отыскать идеальный элемент для устройства/проекта/ритуала", "Построить нечто прекрасное где-то",
        "Завершить дело всей жизни", "Одарить неимущих", "Подняться по карьерной лестнице", "Отыскать способ остаться в этом городе/измерении/мире",
        "Воссоединиться с возлюбленным", "Поделиться с миром своей болью"
    )

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/nature_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  nature")

        val nature = natures.random()
        val demeanor = natures.random()
        val motive = motives.random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Натура: $nature\nМаска: $demeanor\nМотив: $motive")
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
            val motive = motives.random()

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("Натура: $nature\nМаска: $demeanor\nМотив: $motive")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

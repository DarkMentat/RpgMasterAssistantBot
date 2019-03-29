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

class AppearanceHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/appearance_get_new")

    private val genders = listOf(
        "Мужчина", "Мужчина", "Мужчина", "Женщина", "Женщина", "Женщина", "Непонятный", "Трансгендер", "Скрытый", "Андрогин"
    )

    private val faces = listOf(
        "Сильное", "Строгое", "Жесткое", "Мягкое", "Аристократическое", "Утонченное", "Поразительное", "Милое", "Странное", "Изящное", "Прекрасное", "Красивое", "Роскошное",
        "Узкое", "Потрёпанное", "Жуликоватое", "Невинное", "Грязное", "Решительное", "Открытое", "Аскетичное", "Честное", "Грубое", "В шрамах", "Гладкое", "Бледное",
        "Костистое", "Пухлое", "Влажное", "Костлявое", "Тупое", "Изуродованное", "Простое", "Выразительное", "Умное", "Девчачье", "Мальчишеское", "Обветренное", "Морщинистое"
    )

    private val eyes = listOf(
        "Прохладные", "Повелительные", "Апатичные", "Острые", "Прощающие", "Добрые", "Смеющиеся", "Насмешливые", "Тёмные", "Омрачённые", "Беспокойные", "Притягательные",
        "Яркие", "Спокойные", "Прикрытые", "Жёсткие", "Грустные", "Холодные", "Бледные", "Завораживающие", "Оцепенелые", "Подозрительные", "Ясные", "Пылающие", "Расчётливые",
        "Тёплые", "Колючие", "Насторожённые", "Усталые", "Мягкие", "Мёртвые", "Глубокие", "Заботливые", "Пустые", "Влажные", "Безумные", "Яростные", "Мудрые", "Хитрые",
        "Поросячьи глазки", "Прищуренные", "Пляшущие", "Быстрые", "Оценивающие", "Безжалостные", "Ледяные", "Безразличные", "Узкие", "Выгоревшие"
    )

    private val bodies = listOf(
        "Массивное", "Рыхлое", "Жилистое", "Толстое", "Высокое", "Громозкое", "Чувственное", "Стройное", "Подтянутое", "Неестественное", "Юное", "Роскошное", "Пухлое",
        "Коренастое", "Плотное", "Сильное", "Костистое", "Долговязое", "Мягкое", "Грациозное", "Мускулистое", "Поджарое", "Энергичное", "Полное", "Крепкое", "Неуклюжее",
        "Угловатое", "Худощавое", "Покалеченное", "Твёрдое", "Перекачанное", "Компактное", "Огромное", "Хрупкое", "Горбатое", "Приземистое", "Странное", "Милое"
    )

    private val clothes = listOf(
        "Роскошная", "Экстравагантная", "Фетиш", "Повседневная", "Военная форма", "Сценическая", "Экстравагантная истрёпанная", "Истрёпанная", "Винтажная", "Рабочая",
        "Кожаная", "Потрепанная", "Формальная", "Подобранная", "Техническая", "Аскетическая", "Характерная", "Исключительно торжественнная", "Больничная", "Выбивающаяся из окружения",
        "Старинная", "Выпендрёжная", "Пышная", "Доспехи напоказ", "Боевой прикид байкера", "Понтовый прикид байкера", "Старый прикид байкера", "Садо-мазо прикид байкера"
    )

    private val manners = listOf(
        "Постоянно матерится", "Вежливый и корректный", "Необщительный, замкнутый", "Говорит без остановки", "Что-то жует", "Плюется",
        "Курит как паровоз", "Чешется", "Избегает смотреть в глаза", "Говорит короткими предложениями", "Странный акцент ", "Все записывает в книжечку",
        "Теребит четки или еще что-то подобное", "Слегка дрожит", "Избегает прямых формулировок", "Желчный, всех оскорбляет", "Флегматичный", "Вспыльчивый",
        "Шутит по поводу и без", "Лишен чувства юмора"
    )

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/appearance_get_new")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  appearance")

        val gender = genders.random()
        val clothes = clothes.random()
        val body = bodies.random()
        val face = faces.random()
        val eyes = eyes.random()
        val manners = manners.random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Пол: $gender\nОдежда: $clothes\nТело: $body\nЛицо: $face\nГлаза: $eyes\nПовадки: $manners")
            .setReplyMarkup(inlineUpdateButton)
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/appearance_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            val gender = genders.random()
            val clothes = clothes.random()
            val body = bodies.random()
            val face = faces.random()
            val eyes = eyes.random()
            val manners = manners.random()

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("Пол: $gender\nОдежда: $clothes\nТело: $body\nЛицо: $face\nГлаза: $eyes\nПовадки: $manners")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

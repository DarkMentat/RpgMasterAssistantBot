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
        //Маски из корника ВтМ
        "Архитектор", "Автократ", "Бон Вивант", "Браво", "Опекун", "Проповедник", "Ребенок", "Победитель", "Конформист", "Ловкач", "Брюзга",
        "Асоциал", "Директор", "Фанатик", "Щеголь", "Судья", "Одиночка", "Мученик", "Мазохист", "Монстр", "Педагог", "Кающийся грешник", "Перфекционист",
        "Бунтарь", "Плут", "Мастер выживания", "Авантюрист", "Традиционалист", "Шутник", "Мечтатель", "Идеалист", "Солдат", "Дилетант", "Естествоиспытатель",
        "Капиталист", "Хамелеон", "Калейдоскоп ужасов", "Загадка", "Глаз бури", "Гуру", "Садист", "Социопат", "Артист", "Трус", "Исследователь",
        "Фаталист", "Футурист", "Чужак", "Вопрошающий", "Варвар", "Защитник", "Новатор", "Сорвиголова"
    )

    private val motives = listOf(
        //Мотивы из Городских Легенд
        "Получить больше власти/денег/влияния", "Создать себе имя", "Отомстить за брата/сестру/родителей/возлюбленных", "Разрушить систему",
        "Защитить независимое место или заведение", "Получить кровь/наркотики", "Заняться сексом", "Найти способ прекратить этот кошмар",
        "Упиваться ненужной роскошью", "Защитить свою репутацию", "Добиться справедливости для обиженных", "Дать/предложить прощение виновным",
        "Выяснить правду о ком-то", "Отыскать идеальный элемент для устройства/проекта/ритуала", "Построить нечто прекрасное где-то",
        "Завершить дело всей жизни", "Одарить неимущих", "Подняться по карьерной лестнице", "Отыскать способ остаться в этом городе/измерении/мире",
        "Воссоединиться с возлюбленным", "Поделиться с миром своей болью",

        //Цели из генераторов Грани Вселенной
        "Получить могущество", "Получить что-то", "Сблизиться с кем-то", "Удержать могущество", "Удержать кого-то", "Удержать что-то", "Обезопасить / спасти что-то",
        "Обезопасить / спасти кого-то", "Найти / вернуть что-то", "Найти / вернуть близкого", "Уничтожить что-то", "Уничтожить кого- то", "Избавиться от чего-то",
        "Уйти от чьего-то внимания", "Добиться успеха / могущества для кого-то", "Отомстить за кого-то", "Помочь кому-то", "Помочь обездоленным", "Стать частью чего-то большего",
        "Жить обычной жизнью", "Жить красивой / необычной жизнью", "Изменить мир для себя", "Изменить мир к лучшему", "Изменить себя к лучшему", "Добиться справедливости",
        "Сорвать чьи-то планы", "Сохранить секрет в тайне / обмануть", "Раскрыть секрет / узнать правду", "Исправить ошибку", "Вернуть долг", "Создать что-то самостоятельно",
        "Заявить о себе", "Распространить идею / воспитать кого-то", "Найти своё место", "Исполнять долг", "Закончить начатое"
    )

    private val fears = listOf(
        //Страхи из генераторов Грани Вселенной
        "Лишиться могущества", "Скучно жить", "Быть пойманным", "Лишиться чего-то", "Обмануться в идеалах", "Быть непонимаемым", "Лишиться кого-то",
        "Оказаться в какой-то опасности", "Быть преданным", "Упустить шанс", "Проиграть противнику", "Непоправимо измениться", "Совершить фатальную ошибку",
        "Не найти желаемого", "Столкнуться с чем-то ужасным", "Не справиться с делом", "Что кто-то узнает правду", "Умереть"
    )

    private val tempers = listOf(
        //Характер из генераторов Грани Вселенной
        "Прямой", "Неискренний", "Весёлый", "Скептичный", "Упрямый", "Хитрый", "Любопытный", "Располагающий", "Смелый", "Задумчивый", "Наивный", "Холодный",
        "Мягкий", "Угрюмый", "Разговорчивый", "Ленивый", "Умиротворённый", "Прозорливый", "Трусливый", "Вызывающий", "Небрежный", "Склочный", "Мечтательный",
        "Переменчивый", "Отзывчивый", "Тщеславный", "Жестокий", "Расчётливый", "Отталкивающий", "Лихой", "Рассеянный", "Надменный", "Наглый", "Неадекватный",
        "Скрытный", "Завистливый"
    )

    private val manners = listOf(
        "Постоянно матерится", "Вежливый и корректный", "Необщительный, замкнутый", "Говорит без остановки", "Что-то жует", "Плюется",
        "Курит как паровоз", "Чешется", "Избегает смотреть в глаза", "Говорит короткими предложениями", "Странный акцент ", "Все записывает в книжечку",
        "Теребит четки или еще что-то подобное", "Слегка дрожит", "Избегает прямых формулировок", "Желчный, всех оскорбляет", "Флегматичный", "Вспыльчивый",
        "Шутит по поводу и без", "Лишен чувства юмора",

        //Поведение из генераторов Грани Вселенной
        "Неуверенная речь", "Грубая речь", "Очень вежливая речь", "Невнятная речь / плохая дикция", "Заикание", "Кашель", "Слова-паразиты", "Размашистые жесты",
        "Постоянно параллельно чем-то занят", "Забывчивость или невнимательность", "Импульсивность, перепады настроения", "Не улыбается / всегда улыбается"
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
        val manners = manners.random()
        val fear = fears.random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("Натура: $nature\nМаска: $demeanor\nПовадки: $manners\nМотив: $motive\nБоится: $fear")
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
            val manners = manners.random()
            val fear = fears.random()

            sender.execute(
                SendMessage()
                    .setChatId(callbackQuery.message.chatId)
                    .setText("Натура: $nature\nМаска: $demeanor\nПовадки: $manners\nМотив: $motive\nБоится: $fear")
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}

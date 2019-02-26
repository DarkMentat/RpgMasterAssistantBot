package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


class EmogenHandler {

    private val emotions = mapOf(
        "Гнев" to mapOf(
            "Спад" to listOf("Предательство", "Обида"),
            "Унижение" to listOf("Неуважение", "Насмешка"),
            "Горечь" to listOf("Возмущение", "Вторжение"),
            "Безумие" to listOf("Ярость", "Зависть"),
            "Агрессивность" to listOf("Провокация", "Враждебность"),
            "Разочарование" to listOf("Бешенство", "Раздражение"),
            "Дистанция" to listOf("Замкнутость", "Оцепенение"),
            "Критика" to listOf("Скептис", "Пренебрежение")
        ),
        "Страх" to mapOf(
            "Испуг" to listOf("Беспомощность", "Внезапный страх"),
            "Волнение" to listOf("Ошеломление", "Потрясение"),
            "Опасность" to listOf("Неадекватность", "Несоответствие"),
            "Слабость" to listOf("Бесполезность", "Ничтожность"),
            "Отрицание" to listOf("Изоляция", "Преследование"),
            "Угроза" to listOf("Нервозность", "Незащищенность")
        ),
        "Негатив" to mapOf(
            "Скука" to listOf("Равнодушие", "Апатия"),
            "Назойливость" to listOf("Давление", "Спешка"),
            "Стресс" to listOf("Ошеломление", "Бесконтрольность"),
            "Усталость" to listOf("Сонность", "Расфокусированность")
        ),
        "Удивление" to mapOf(
            "Поражение" to listOf("Шок", "Смятение"),
            "Замешательство" to listOf("Разочарование", "Недоумение"),
            "Изумление" to listOf("Потрясение", "Благоговение"),
            "Восторг" to listOf("Нетерпение", "Энергия")
        ),
        "Счастье" to mapOf(
            "Игривость" to listOf("Возбуждение", "Развязность"),
            "Удовлетворение" to listOf("Свобода", "Радость"),
            "Интерес" to listOf("Любопытство", "Пытливость"),
            "Гордость" to listOf("Успешность", "Самоуверенность"),
            "Принятие" to listOf("Уважение", "Ценность"),
            "Мощь" to listOf("Отвага", "Творчество"),
            "Мир" to listOf("Любовь", "Благодарность"),
            "Доверие" to listOf("Чуткость", "Близость"),
            "Оптимизм" to listOf("Надежда", "Вдохновение")
        ),
        "Печаль" to mapOf(
            "Одиночество" to listOf("Изоляция", "Брошенность"),
            "Уязвимость" to listOf("Преследование", "Слабость"),
            "Отчаяние" to listOf("Горе", "Бессилие"),
            "Вина" to listOf("Раскаяние", "Совестность"),
            "Подавленность" to listOf("Подчинение", "Пустота"),
            "Обида" to listOf("Пустота", "Разочарование")
        ),
        "Мерзость" to mapOf(
            "Неодобрение" to listOf("Осуждение", "Смущение"),
            "Разочарование" to listOf("Потрясение", "Восстание"),
            "Ужас" to listOf("Тошнота", "Отвращение"),
            "Отторжение" to listOf("ужасе", "Сомнение")
        )
    )



    fun process(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND:  emogen")

        val first = emotions.keys.random()
        val second = emotions.getValue(first).keys.random()
        val third = emotions.getValue(first).getValue(second).random()

        return SendMessage()
            .setChatId(update.message.chatId)
            .setText("$first -> $second -> $third")
    }
}
























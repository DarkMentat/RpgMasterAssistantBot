package org.darkmentat

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

class MainMenuKeyboard : ReplyKeyboardMarkup() {

    val btnTarot = "Get Tarot Card"
    val btnEmogen = "Emogen"
    val btnAppearance = "Appearance"
    val btnNature = "Nature/Demeanor"
    val btnRandomName = "Random name"

    val cmdTarot = "/tarot"
    val cmdEmogen = "/emogen"
    val cmdAppearance = "/appearance"
    val cmdNature = "/nature"
    val cmdRandomName = "/random_name"

    private val mainMenuKeyboard = arrayListOf(
        KeyboardRow().apply {
            add(btnTarot)
            add(btnEmogen)
        },
        KeyboardRow().apply {
            add(btnAppearance)
            add(btnNature)
        },
        KeyboardRow().apply {
            add(btnRandomName)
        }
    )

    init {
        keyboard = mainMenuKeyboard
        resizeKeyboard = true
        oneTimeKeyboard = false
    }

    fun textToCommand(text: String): String {

        return when (text) {
            btnTarot -> cmdTarot
            btnEmogen -> cmdEmogen
            btnAppearance -> cmdAppearance
            btnNature -> cmdNature
            btnRandomName -> cmdRandomName
            else -> text
        }
    }
}
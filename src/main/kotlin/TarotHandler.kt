package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random

class TarotHandler(
    private val sender: AbsSender,
    private val keyboardMarkup: ReplyKeyboardMarkup
): Handler {

    override val callbackCommands = listOf("/tarot_get_new", "/tarot_get_new_witch")

    private val tarotImageBaseUrl = "https://i.ibb.co/"
    private val tarotImages = listOf(
        "PgP6nVv/78.jpg", "Y0SSvvZ/77.jpg", "cYsDZXJ/76.jpg", "8dCRj5x/74.jpg", "99PGhM0/75.jpg", "J7NKw7F/73.jpg", "CWG2xT1/72.jpg", "DpNwjdy/71.jpg", "wsJqkp0/70.jpg", "5KghH0W/69.jpg",
        "P5LbZpx/68.jpg", "qjybSG2/67.jpg", "GWTph8H/66.jpg", "G3v56Cd/65.jpg", "XbZDXWn/64.jpg", "dpZnJKp/63.jpg", "C1G2KFq/62.jpg", "bPV169r/61.jpg", "xJkwh7B/60.jpg", "ZzC64RK/59.jpg",
        "xqMXx2C/58.jpg", "V2WqDWW/57.jpg", "1Rb6bnj/56.jpg", "30qGTPv/55.jpg", "xLz9tjq/54.jpg", "jb1j71y/53.jpg", "cx2dNDY/52.jpg", "TWC7gm6/51.jpg", "TbCjGjN/50.jpg", "6y3rCMc/49.jpg",
        "L9STMmn/48.jpg", "7W3pK4M/47.jpg", "ZdNVkd4/46.jpg", "WpwZb33/45.jpg", "BNdFKcq/44.jpg", "hHCZWH1/43.jpg", "9ZwPZPz/42.jpg", "v4vmXXx/41.jpg", "CnrC0m7/40.jpg", "wyMFcK7/39.jpg",
        "ncjLgBK/38.jpg", "fqknjcK/37.jpg", "2yhrFYX/36.jpg", "yWL62dt/35.jpg", "J3zq6Wk/34.jpg", "41J4hF8/33.jpg", "D1bkYqy/32.jpg", "DWcHx4g/31.jpg", "LY3w6Zk/30.jpg", "k6dFZsB/29.jpg",
        "MZPQbGW/28.jpg", "tcntdmT/27.jpg", "ChrXph2/25.jpg", "HT9bksv/26.jpg", "44DqHz0/24.jpg", "x51ZLzM/23.jpg", "cFt5jYg/22.jpg", "mbHFB79/21.jpg", "kSpDHFR/20.jpg", "2666JhP/19.jpg",
        "yRFMBDf/18.jpg", "NxqM296/17.jpg", "SsdZxP4/16.jpg", "54z0n18/15.jpg", "LR1n66d/14.jpg", "KGPV5WH/13.jpg", "6XKnzR7/12.jpg", "9t8FG4h/11.jpg", "zGSY7WF/10.jpg", "8xJ9p90/9.jpg",
        "P9YYfz6/8.jpg", "9VHCF41/7.jpg", "4jv0jgh/6.jpg", "8N56R5x/5.jpg", "JK6SR6M/4.jpg", "Qj9rXHK/3.jpg", "pK3jsTp/2.jpg", "sRLr2xL/1.jpg"
    )
    private val tarotImagesWitches = listOf(
        "Xzwtgjd/76.jpg", "mbfJ0sW/75.jpg", "K28hTMw/74.jpg", "Z8F5My1/73.jpg", "cxJRxKg/72.jpg", "RBW0Qcn/71.jpg", "09ZwpyJ/70.jpg", "g9YD8pc/69.jpg", "NCfD6xy/68.jpg", "XZY4MDP/67.jpg",
        "55K1dxv/66.jpg", "3mxK9M3/65.jpg", "QdRwBD0/64.jpg", "CKy2fS8/63.jpg", "5RK0M0V/62.jpg", "VCBHpFx/61.jpg", "M8NzSWN/60.jpg", "JzPB2zT/59.jpg", "ZXgGGBn/58.jpg", "hW7Mjkb/57.jpg",
        "0c3DBp1/56.jpg", "Pzt8MKh/55.jpg", "HGs8kjW/54.jpg", "5T3FnrG/53.jpg", "YNLTN1W/52.jpg", "TwxgL1B/51.jpg", "qxS8Wmm/50.jpg", "DthnZbV/49.jpg", "MsMBD4j/48.jpg", "3CBKSQZ/47.jpg",
        "tmRhwJX/46.jpg", "M2K38Qn/45.jpg", "fCRYT5N/44.jpg", "J79r3MT/43.jpg", "6mWR42r/42.jpg", "rvQtjLP/41.jpg", "hMtjM6B/40.jpg", "jJxjSjT/39.jpg", "X3QX2Qh/38.jpg", "c2gvXWg/37.jpg",
        "28jhMpn/36.jpg", "8XMhJxX/35.jpg", "1TjqnBt/34.jpg", "xMKy2YG/33.jpg", "hMjCj41/32.jpg", "DtbHLzD/31.jpg", "bdhj9M9/30.jpg", "SmTF5pJ/29.jpg", "jgZSZC8/28.jpg", "M1nPrsc/27.jpg",
        "nDKjYT3/26.jpg", "2hnMDKC/25.jpg", "Mc6x3NQ/24.jpg", "GkPcHLQ/23.jpg", "6WVJcRH/22.jpg", "RDpHDPV/21.jpg", "88BfJF1/20.jpg", "6yDQpcQ/19.jpg", "gr4vCrH/18.jpg", "58mXXwz/17.jpg",
        "g6n1pGC/16.jpg", "NKT1NBm/15.jpg", "fk2LPPw/14.jpg", "PYjQcHL/13.jpg", "QmgjjS3/12.jpg", "fdvsKjK/11.jpg", "j67LTxm/10.jpg", "FYYhFTc/9.jpg", "q0nYJPH/8.jpg", "CnDt1MH/7.jpg",
        "tKZsRZ8/6.jpg", "mb0WqZQ/5.jpg", "M6KpZPz/4.jpg", "tXhSx71/3.jpg", "sgHN8nJ/2.jpg", "Y7jgYSR/1.jpg", "qpwCqK4/77.jpg", "brFRhhD/0.jpg"
    )

    private val inlineUpdateButton = InlineKeyboardMarkup().apply {
        keyboard = listOf(
            listOf(
                InlineKeyboardButton().setText("\uD83D\uDD01 Get New!").setCallbackData("/tarot_get_new"),
                InlineKeyboardButton().setText("\uD83E\uDDD9\u200D♀ Witch!").setCallbackData("/tarot_get_new_witch")
            )
        )
    }

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
        println("SEND: tarot card")

        sender.execute(SendPhoto()
            .setPhoto(tarotImageBaseUrl + tarotImages[Random.nextInt(tarotImages.size)])
            .setChatId(update.message.chatId)
            .setReplyMarkup(inlineUpdateButton)
        )

        return null
    }

    override fun processCallback(callbackQuery: CallbackQuery) {
        if(callbackQuery.data == "/tarot_get_new") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            sender.execute(
                SendPhoto()
                    .setChatId(callbackQuery.message.chatId)
                    .setPhoto(tarotImageBaseUrl + tarotImages[Random.nextInt(tarotImages.size)])
                    .setChatId(callbackQuery.message.chatId)
                    .setReplyMarkup(inlineUpdateButton)
            )
        }else if(callbackQuery.data == "/tarot_get_new_witch") {

            sender.execute(
                EditMessageReplyMarkup()
                    .setChatId(callbackQuery.message.chatId)
                    .setMessageId(callbackQuery.message.messageId)
                    .setReplyMarkup(InlineKeyboardMarkup())
            )

            sender.execute(
                SendPhoto()
                    .setChatId(callbackQuery.message.chatId)
                    .setPhoto(tarotImageBaseUrl + tarotImagesWitches[Random.nextInt(tarotImagesWitches.size)])
                    .setChatId(callbackQuery.message.chatId)
                    .setReplyMarkup(inlineUpdateButton)
            )
        }
    }
}
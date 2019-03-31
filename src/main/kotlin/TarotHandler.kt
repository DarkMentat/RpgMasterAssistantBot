package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
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
    private val tarotImagesRaider = listOf(
        "YpZHh0Z/rider-waite-minor-arcana-wands-14-king-6234659884-o.jpg",
        "P1MtGRV/rider-waite-minor-arcana-wands-13-queen-6234133349-o.jpg",
        "zmnSHkP/rider-waite-minor-arcana-wands-12-knight-6234131585-o.jpg",
        "crWMHsB/rider-waite-minor-arcana-wands-11-page-6234129705-o.jpg",
        "5WXBpwy/rider-waite-minor-arcana-wands-10-6234651626-o.jpg",
        "gDfB3CZ/rider-waite-minor-arcana-wands-09-6234125333-o.jpg",
        "nB984NV/rider-waite-minor-arcana-wands-08-6234123431-o.jpg",
        "rkvVpmZ/rider-waite-minor-arcana-wands-07-6234645990-o.jpg",
        "9qYZt5R/rider-waite-minor-arcana-wands-06-6234119573-o.jpg",
        "CtkvynQ/rider-waite-minor-arcana-wands-05-6234117635-o.jpg",
        "qJkkhjK/rider-waite-minor-arcana-wands-04-6234115553-o.jpg",
        "bLqwH0v/rider-waite-minor-arcana-wands-03-6234113573-o.jpg",
        "4mv8DqC/rider-waite-minor-arcana-wands-02-6234636276-o.jpg",
        "pzhvXSz/rider-waite-minor-arcana-wands-01-ace-6234634378-o.jpg",
        "TqRwNGS/rider-waite-minor-arcana-cups-14-king-6234708876-o.jpg",
        "R0sKz52/rider-waite-minor-arcana-cups-13-queen-6234181309-o.jpg",
        "c8zP0jH/rider-waite-minor-arcana-cups-12-knight-6234177439-o.jpg",
        "4Mfbgdb/rider-waite-minor-arcana-cups-11-page-6234174547-o.jpg",
        "jRb90KN/rider-waite-minor-arcana-cups-10-6234171803-o.jpg",
        "gtsXbZR/rider-waite-minor-arcana-cups-09-6234168247-o.jpg",
        "QMt1Dx7/rider-waite-minor-arcana-cups-08-6234165989-o.jpg",
        "tDdxHT4/rider-waite-minor-arcana-cups-07-6234162753-o.jpg",
        "BTDDkgT/rider-waite-minor-arcana-cups-06-6234681960-o.jpg",
        "xC1hxpC/rider-waite-minor-arcana-cups-05-6234679202-o.jpg",
        "SxGwc8t/rider-waite-minor-arcana-cups-04-6234675624-o.jpg",
        "MktKnBC/rider-waite-minor-arcana-cups-03-6234672074-o.jpg",
        "YBH4CTk/rider-waite-minor-arcana-cups-02-6234669216-o.jpg",
        "wLXSMRB/rider-waite-minor-arcana-cups-01-ace-6234141143-o.jpg",
        "WkLy7MH/rider-waite-minor-arcana-swords-14-king-6234228441-o.jpg",
        "GdxdNRr/rider-waite-minor-arcana-swords-13-queen-6234749846-o.jpg",
        "s5PnX72/rider-waite-minor-arcana-swords-12-knight-6234222761-o.jpg",
        "475XFS0/rider-waite-minor-arcana-swords-11-page-6234743864-o.jpg",
        "PMkMfV3/rider-waite-minor-arcana-swords-10-6234741256-o.jpg",
        "B6SmCdv/rider-waite-minor-arcana-swords-09-6234213789-o.jpg",
        "JkGQ3QN/rider-waite-minor-arcana-swords-08-6234210379-o.jpg",
        "fFJ3cbH/rider-waite-minor-arcana-swords-07-6234207565-o.jpg",
        "XCyQ9Vr/rider-waite-minor-arcana-swords-06-6234729032-o.jpg",
        "k92RZH7/rider-waite-minor-arcana-swords-05-6234201033-o.jpg",
        "vPLyGxG/rider-waite-minor-arcana-swords-04-6234722694-o.jpg",
        "fqHHY3n/rider-waite-minor-arcana-swords-03-6234719796-o.jpg",
        "02kfvvY/rider-waite-minor-arcana-swords-02-6234716234-o.jpg",
        "C8WMV7M/rider-waite-minor-arcana-swords-01-ace-6234712912-o.jpg",
        "LYQ3qhJ/rider-waite-minor-arcana-pentacles-14-king-6234270179-o.jpg",
        "k1wCpyC/rider-waite-minor-arcana-pentacles-13-queen-6234266673-o.jpg",
        "9n90C9d/rider-waite-minor-arcana-pentacles-12-knight-6234264177-o.jpg",
        "4MSD2mS/rider-waite-minor-arcana-pentacles-11-page-6234261387-o.jpg",
        "51zr4Yv/rider-waite-minor-arcana-pentacles-10-6234259139-o.jpg",
        "H2qyBBh/rider-waite-minor-arcana-pentacles-09-6234255825-o.jpg",
        "105QVM6/rider-waite-minor-arcana-pentacles-08-6234777816-o.jpg",
        "njY3N5B/rider-waite-minor-arcana-pentacles-07-6234250873-o.jpg",
        "rQnK11z/rider-waite-minor-arcana-pentacles-06-6234772038-o.jpg",
        "X7zmtGg/rider-waite-minor-arcana-pentacles-05-6234768838-o.jpg",
        "m6ZZvw3/rider-waite-minor-arcana-pentacles-04-6234766364-o.jpg",
        "7QFT9h3/rider-waite-minor-arcana-pentacles-03-6234238151-o.jpg",
        "VJLR2K2/rider-waite-minor-arcana-pentacles-02-6234759442-o.jpg",
        "xzWgPmB/rider-waite-minor-arcana-pentacles-01-ace-6234755746-o.jpg",
        "F5ghLmV/rider-waite-major-arcana-21-the-world-6234101445-o.jpg",
        "qNsPdgr/rider-waite-major-arcana-20-judgement-6234099599-o.jpg",
        "Z89JZWv/rider-waite-major-arcana-19-the-sun-6234097611-o.jpg",
        "SJV6vJp/rider-waite-major-arcana-18-the-moon-6234620480-o.jpg",
        "qmKMVyr/rider-waite-major-arcana-17-the-star-6234094083-o.jpg",
        "fvBM8X6/rider-waite-major-arcana-16-the-tower-6234092237-o.jpg",
        "2trN1Tb/rider-waite-major-arcana-15-the-devil-6234090311-o.jpg",
        "q9BdY8N/rider-waite-major-arcana-14-temperance-6234088279-o.jpg",
        "dp6CRJq/rider-waite-major-arcana-13-death-6234086467-o.jpg",
        "d2z7Rc0/rider-waite-major-arcana-12-the-hanged-man-6234084897-o.jpg",
        "W3C9gbs/rider-waite-major-arcana-11-justice-6234605714-o.jpg",
        "NLzZFsV/rider-waite-major-arcana-10-wheel-of-fortune-6234602206-o.jpg",
        "PxZ5kJz/rider-waite-major-arcana-09-the-hermit-6234075619-o.jpg",
        "9HLLsXV/rider-waite-major-arcana-08-strength-6234598040-o.jpg",
        "GpD9MQ9/rider-waite-major-arcana-07-the-chariot-6234072405-o.jpg",
        "4JVcZVx/rider-waite-major-arcana-06-the-lovers-6234595528-o.jpg",
        "TTFZcQR/rider-waite-major-arcana-05-the-hierophant-6234068727-o.jpg",
        "LJsknNf/rider-waite-major-arcana-04-the-emperor-6234066109-o.jpg",
        "jZKPpjp/rider-waite-major-arcana-03-the-empress-6234588362-o.jpg",
        "BwG1ckM/rider-waite-major-arcana-02-the-high-priestess-6234585244-o.jpg",
        "KwFCxsh/rider-0-waite-major-arcana-01-the-magician-6234582750-o.jpg",
        "9VL9bfZ/rider-waite-major-arcana-00-the-fool-6234580670-o.jpg"
    )
    private val tarotImagesNewVision = listOf(
        "PgP6nVv/78.jpg", "Y0SSvvZ/77.jpg", "cYsDZXJ/76.jpg", "99PGhM0/75.jpg", "8dCRj5x/74.jpg", "J7NKw7F/73.jpg", "CWG2xT1/72.jpg", "DpNwjdy/71.jpg", "wsJqkp0/70.jpg", "5KghH0W/69.jpg",
        "P5LbZpx/68.jpg", "qjybSG2/67.jpg", "GWTph8H/66.jpg", "G3v56Cd/65.jpg", "XbZDXWn/64.jpg", "dpZnJKp/63.jpg", "C1G2KFq/62.jpg", "bPV169r/61.jpg", "xJkwh7B/60.jpg", "ZzC64RK/59.jpg",
        "xqMXx2C/58.jpg", "V2WqDWW/57.jpg", "1Rb6bnj/56.jpg", "30qGTPv/55.jpg", "xLz9tjq/54.jpg", "jb1j71y/53.jpg", "cx2dNDY/52.jpg", "TWC7gm6/51.jpg", "TbCjGjN/50.jpg", "6y3rCMc/49.jpg",
        "L9STMmn/48.jpg", "7W3pK4M/47.jpg", "ZdNVkd4/46.jpg", "WpwZb33/45.jpg", "BNdFKcq/44.jpg", "hHCZWH1/43.jpg", "9ZwPZPz/42.jpg", "v4vmXXx/41.jpg", "CnrC0m7/40.jpg", "wyMFcK7/39.jpg",
        "ncjLgBK/38.jpg", "fqknjcK/37.jpg", "2yhrFYX/36.jpg", "yWL62dt/35.jpg", "J3zq6Wk/34.jpg", "41J4hF8/33.jpg", "D1bkYqy/32.jpg", "DWcHx4g/31.jpg", "LY3w6Zk/30.jpg", "k6dFZsB/29.jpg",
        "MZPQbGW/28.jpg", "tcntdmT/27.jpg", "HT9bksv/26.jpg", "ChrXph2/25.jpg", "44DqHz0/24.jpg", "x51ZLzM/23.jpg", "cFt5jYg/22.jpg", "mbHFB79/21.jpg", "kSpDHFR/20.jpg", "2666JhP/19.jpg",
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

        if(update.message.text == "/tarot_get_pair"){
            val album = SendMediaGroup()

            val index = Random.nextInt(0, tarotImagesRaider.size)

            album.media = listOf(
                InputMediaPhoto(tarotImageBaseUrl + tarotImagesRaider[index], ""),
                InputMediaPhoto(tarotImageBaseUrl + tarotImagesNewVision[index], "")
            )

            album.setChatId(update.message.chatId)

            sender.execute(album)
            return null
        }


        sender.execute(SendPhoto()
            .setPhoto(tarotImageBaseUrl + tarotImagesNewVision[Random.nextInt(tarotImagesNewVision.size)])
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
                    .setPhoto(tarotImageBaseUrl + tarotImagesNewVision[Random.nextInt(tarotImagesNewVision.size)])
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
package org.darkmentat

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.bots.AbsSender
import kotlin.random.Random

class TarotHandler(private val sender: AbsSender): Handler {

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

    override fun processDirect(update: Update): BotApiMethod<out BotApiObject>? {
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

        album.setChatId(update.message.chatId)

        sender.execute(album)

        return null
    }
}
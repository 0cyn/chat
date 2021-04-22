package dev.phoenix.chat.chat

object ChatFilter {
    fun isBegging(message: String): Double {
        val meArray = arrayOf("me", "i")
        val inquire = arrayOf("would", "could", "can", "?", "plz", "pls", "please", "have", "has", "want")
        val topic = arrayOf("rank", "vip", "vip+", "mvp", "mvp+", "mvp++", "++")
        val modifiers = arrayOf("give", "gift", "have", "give", "gift", "free", "buy", "giv", "pls", "plz")
        val inp = message.toLowerCase()
        var conf: Double = 0.0
        for (i in inquire)
            for (j in inp.split(' '))
                if (j.contains(i))
                    conf+=1
        for (i in modifiers)
            for (j in inp.split(' '))
                if (j.contains(i))
                    conf = conf * 3 + 1
        for (i in topic)
            for (j in inp.split(' '))
                if (j.contains(i))
                    conf = conf * 2 + .5
        for (i in meArray)
            for (j in inp.split(' '))
                if (j.contains(i))
                    conf+=1

        var count = inp.count{ c -> c == ' ' }
        if (count == 0)
            count = 1
        conf /= count
        return conf
    }
}
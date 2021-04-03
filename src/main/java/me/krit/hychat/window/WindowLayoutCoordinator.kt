package me.krit.hychat.window

import me.krit.hychat.ui.HyChatFrame
import me.krit.hychat.server.chat.ChatContext

class WindowLayoutCoordinator private constructor() {
    val frame: HyChatFrame
    fun createTabForChatContext(context: ChatContext) {
        frame.createTabWithName(context.title)
    }

    fun displayLineFromContext(context: ChatContext, message: String) {
        frame.addLineToTabWithName(context.title, """
     $message
     
     """.trimIndent())
    }

    companion object {
        @JvmStatic
        val instance = WindowLayoutCoordinator()
    }

    init {
        frame = HyChatFrame()
    }
}
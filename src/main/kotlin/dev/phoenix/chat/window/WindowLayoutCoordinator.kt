package dev.phoenix.chat.window

import dev.phoenix.chat.ui.ChatFrame
import dev.phoenix.chat.server.chat.ChatContext

object WindowLayoutCoordinator {

    val frame: ChatFrame = ChatFrame()
    fun createTabForChatContext(context: ChatContext) {
        frame.createTabWithName(context.title)
    }

    fun displayLineFromContext(context: ChatContext, message: String) {
        frame.addLineToTabWithName(context.title, """
     $message
     
     """.trimIndent())
    }

}
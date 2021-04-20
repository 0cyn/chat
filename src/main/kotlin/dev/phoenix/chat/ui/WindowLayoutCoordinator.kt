package dev.phoenix.chat.ui

import dev.phoenix.chat.server.chat.ChatContext

/**
 * General shared window layout coordinator object
 * 
 * This should handle a lot of layout stuff (move more things here)
 */
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
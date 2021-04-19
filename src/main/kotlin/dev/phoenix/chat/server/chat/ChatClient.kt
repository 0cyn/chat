package dev.phoenix.chat.server.chat

import dev.phoenix.chat.Client
import dev.phoenix.chat.util.ChatMessage
import dev.phoenix.chat.server.chat.ChatContext
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ChatClient(title: String, private val chatPrefix: String, var type: ChatType, sendToContextCommand: String) {
    var context: ChatContext = ChatContext(title, chatPrefix, type, sendToContextCommand)

    init {
        println("Registered ChatClient " + context.title)
    }

    fun shouldHandleChat(message: ChatMessage): Boolean {
        return context.messageQualifiesForContext(message.plaintext)
    }

    fun removePrefix(message: String): String {
        if (type == ChatType.PUBLIC)
            return message.replaceFirst(chatPrefix, "")
        else if (type == ChatType.PRIVATE)
            return message.replaceFirst("To ", "").replaceFirst("From ", "")
        return message
    }

    fun sendMessageToServer(message: String) {
        val msg = context.sendToContextCommand + message
        Client.sendChat(msg)
    }
}
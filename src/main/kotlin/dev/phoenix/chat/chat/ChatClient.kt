package dev.phoenix.chat.server.chat

import dev.phoenix.chat.mod.Client
import dev.phoenix.chat.chat.ChatMessage
import dev.phoenix.chat.server.chat.ChatContext
import dev.phoenix.chat.ui.WindowLayoutCoordinator
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Represents a channel (tab) of chat.
 * 
 * Handles checking incoming messages and sending outbound messages in a specific channel
 */
class ChatClient(title: String, private val chatPrefix: String, var type: ChatType, sendToContextCommand: String) {
    var context: ChatContext = ChatContext(title, chatPrefix, type, sendToContextCommand)

    init {
        println("Registered ChatClient " + context.title)
    }

    fun shouldHandleChat(message: ChatMessage): Boolean {
        if (message.plaintext != null)
            return context.messageQualifiesForContext(message)
        return false
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
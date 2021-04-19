package dev.phoenix.chat.server.chat

import dev.phoenix.chat.Client
import dev.phoenix.chat.server.chat.ChatContext
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent

class ChatClient(title: String, private val chatPrefix: String, var type: ChatType, sendToContextCommand: String) {
    var context: ChatContext = ChatContext(title, chatPrefix, type, sendToContextCommand)


    init {
        println("Registered ChatClient " + context.title)
    }

    fun shouldHandleChat(e: ClientChatReceivedEvent): Boolean {
        if (e.type.toInt() == 0) {
            val message = e.message.unformattedText
            return context.messageQualifiesForContext(message.replace("\\u00A7.".toRegex(), ""))
        }
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
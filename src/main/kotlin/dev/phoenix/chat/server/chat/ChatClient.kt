package dev.phoenix.chat.server.chat

import dev.phoenix.chat.Client
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent

class ChatClient {
    var context: ChatContext

    constructor(context: ChatContext) {
        this.context = context
        println("Registered ChatClient " + context.title)
        WindowLayoutCoordinator.instance.createTabForChatContext(context)
    }

    constructor(title: String, chatPrefix: String, type: ChatType, sendToContextCommand: String) {
        context = ChatContext(title, chatPrefix, type, sendToContextCommand)
        println("Registered ChatClient " + context.title)
        WindowLayoutCoordinator.instance.createTabForChatContext(context)
    }

    @SubscribeEvent
    fun onChat(e: ClientChatReceivedEvent) {
        if (e.type.toInt() == 0) {
            val message = e.message.unformattedText
            handleChat(message)
        }
    }

    fun handleChat(message: String)
    {
        if (context.messageQualifiesForContext(message.replace("\\u00A7.".toRegex(), ""))) displayLineInRelavantTab(message)
    }

    fun displayLineInRelavantTab(message: String) {
        WindowLayoutCoordinator.instance.displayLineFromContext(context, message)
    }

    fun sendMessageToServer(message: String) {
        val msg = context.sendToContextCommand + message
        Client.instance.sendChat(msg)
    }
}
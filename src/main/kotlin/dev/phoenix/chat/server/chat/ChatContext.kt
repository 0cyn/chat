package dev.phoenix.chat.server.chat

class ChatContext(var title: String, var chatPrefix: String, var type: ChatType, var sendToContextCommand: String) {
    fun messageQualifiesForContext(message: String): Boolean {
        println(message)
        return message.startsWith(chatPrefix)
    }
}
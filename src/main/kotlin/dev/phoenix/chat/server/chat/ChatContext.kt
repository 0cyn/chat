package dev.phoenix.chat.server.chat

class ChatContext(var title: String, var chatPrefix: String, var type: ChatType, var sendToContextCommand: String) {
    fun messageQualifiesForContext(message: String): Boolean {
        println(message)

        if (type == ChatType.PRIVATE)
        {
            // TODO: this is a non-portable hack for hypixel
            if (message.startsWith("To ") || message.startsWith("From "))
            {
                return true
            }
        }

        return message.startsWith(chatPrefix)
    }
}
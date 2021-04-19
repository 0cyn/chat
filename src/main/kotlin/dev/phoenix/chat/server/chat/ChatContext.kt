package dev.phoenix.chat.server.chat

class ChatContext(var title: String, var chatPrefix: String, var type: ChatType, var sendToContextCommand: String) {
    fun messageQualifiesForContext(message: String): Boolean {

        if (type == ChatType.PRIVATE)
        {
            // TODO: this is a non-portable hack for hypixel
            // TODO: nons
            // From rank playername:
            if (message.startsWith("To ") || message.startsWith("From "))
            {
                return message.split(' ')[2].dropLast(1) == title
            }
        }

        return message.startsWith(chatPrefix)
    }
}
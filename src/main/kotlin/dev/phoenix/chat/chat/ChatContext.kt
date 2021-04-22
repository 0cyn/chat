package dev.phoenix.chat.server.chat

import dev.phoenix.chat.chat.ChatMessage
import dev.phoenix.chat.mod.Client

/**
 * Holds specific info for a Chat Client
 */
class ChatContext(var title: String, var chatPrefix: String, var type: ChatType, var sendToContextCommand: String) {
    fun messageQualifiesForContext(message: ChatMessage): Boolean {

        if (type == ChatType.PRIVATE)
        {
            if (message.ampFormatted.contains("&dTo") || message.ampFormatted.contains("&dFrom"))
            {
                return message.playerName == title || message.playerName == Client.player?.name
            }
        }

        return message.plaintext.startsWith(chatPrefix)
    }
}
package dev.phoenix.chat.server.chat

/**
 * LOBBY - local server specific *chat*
 * GAME - local server specific messages sent by the server (not users)
 * PUBLIC - full server wide multi-user chat (guild chat, for example)
 * PRIVATE - one-to-one private messaging with another user
 */
enum class ChatType {
    PUBLIC, PRIVATE, LOBBY, GAME
}
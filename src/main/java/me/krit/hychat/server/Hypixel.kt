package me.krit.hychat.server

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import me.krit.hychat.server.chat.ChatClient
import me.krit.hychat.server.chat.ChatType

class Hypixel(client: EntityPlayerSP, server: ServerData) : Server(client, server) {
    override fun configureChatClients() {
        chatClients.add(ChatClient("Lobby", "[", ChatType.PUBLIC, "/achat "))
        chatClients.add(ChatClient("Guild", "Guild >", ChatType.PUBLIC, "/gchat "))
        chatClients.add(ChatClient("Party", "Party >", ChatType.PUBLIC, "/pchat "))
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "/dontusethis", ChatType.PUBLIC, "/dontusethis"))
        }
        registerChatClients()
    }
}
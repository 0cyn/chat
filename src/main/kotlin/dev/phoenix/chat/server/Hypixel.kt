package dev.phoenix.chat.server

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatClient
import dev.phoenix.chat.server.chat.ChatType
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Hypixel(client: EntityPlayerSP, server: ServerData) : Server(client, server) {
    override fun configureChatClients() {
        chatClients.add(ChatClient("Lobby", "[", ChatType.PUBLIC, "/achat "))
        chatClients.add(ChatClient("Guild", "Guild >", ChatType.PUBLIC, "/gchat "))
        chatClients.add(ChatClient("Party", "Party >", ChatType.PUBLIC, "/pchat "))
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "/dontusethis", ChatType.PUBLIC, "/dontusethis"))
        }
        registerChatClients()

        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onChat(e: ClientChatReceivedEvent) {
        if (e.type.toInt() == 0) {
            val message = e.message.unformattedText
            if (message.replace("\\u00A7.".toRegex(), "").startsWith("To ") || message.replace("\\u00A7.".toRegex(), "").startsWith("From "))
            {
                val withUser = message.replace("\\u00A7.".toRegex(), "").split(' ')[2].dropLast(1)
                handleDM(withUser, message)
            }
        }
    }

    fun handleDM(with: String, message: String)
    {
        if (!chatClientMap.containsKey(with))
        {
            val newChatClient = ChatClient(with, "From ", ChatType.PRIVATE, "/msg $with ")
            chatClients.add(newChatClient)
            chatClientMap[with] = newChatClient
            MinecraftForge.EVENT_BUS.register(newChatClient)
            chatClientMap[with]?.handleChat(message)
        }
    }
}
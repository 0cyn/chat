package dev.phoenix.chat.server

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatClient
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Hypixel(client: EntityPlayerSP, server: ServerData) : Server(client, server) {
    override fun configureChatClients() {
        chatClients.add(ChatClient("Lobby", "", ChatType.LOBBY, "/achat "))
        lobbyClient = chatClients[0]
        chatClients.add(ChatClient("Game", "", ChatType.GAME, "/achat "))
        gameClient = chatClients[1]
        chatClients.add(ChatClient("Guild", "Guild >", ChatType.PUBLIC, "/gchat "))
        chatClients.add(ChatClient("Party", "Party >", ChatType.PUBLIC, "/pchat "))
        chatClients.add(ChatClient("Officer", "Officer >", ChatType.PUBLIC, "/oc "))
        chatClients.add(ChatClient("Co-op", "Co-op >", ChatType.PUBLIC, "/cc "))
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "/dontusethis", ChatType.PUBLIC, "/dontusethis"))
        }

        registerChatClients()

        MinecraftForge.EVENT_BUS.register(this)
    }

    override fun unregisterChatClients()
    {
        MinecraftForge.EVENT_BUS.unregister(this)
    }

    @SubscribeEvent
    fun onChat(e: ClientChatReceivedEvent) {
        if (e.type.toInt() == 0) {
            val message = e.message.unformattedText
            val formattedMessage = e.message.formattedText
            if (message.replace("\\u00A7.".toRegex(), "").startsWith("To ") || message.replace("\\u00A7.".toRegex(), "").startsWith("From "))
            {
                // TODO: nons
                val withUser = message.replace("\\u00A7.".toRegex(), "").split(' ')[2].dropLast(1)
                if (withUser == "Be") // "To leave Bed Wars, type /lobby"
                    return
                handleDM(withUser, formattedMessage)
            }
            else
            {
                for (client in chatClients)
                {
                    if (client.type == ChatType.PUBLIC && client.shouldHandleChat(e)) // already got dms, game == catchall
                    {
                        WindowLayoutCoordinator.displayLineFromContext(client.context, client.removePrefix(formattedMessage))
                        return
                    }
                }
                // if we've made it this far, the message qualified for no other chat clients
                if (message.replace("\\u00A7.".toRegex(), "").contains(": "))
                    WindowLayoutCoordinator.displayLineFromContext(lobbyClient.context, formattedMessage)
                else
                    WindowLayoutCoordinator.displayLineFromContext(gameClient.context, formattedMessage)

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
            chatClientMap[with]?.let { WindowLayoutCoordinator.displayLineFromContext(it.context, message) }
        }
    }
}
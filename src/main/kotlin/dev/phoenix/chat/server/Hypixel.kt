package dev.phoenix.chat.server

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatClient
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.util.ChatMessage
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Hypixel(client: EntityPlayerSP, server: ServerData) : Server(client, server) {
    override fun configureChatClients() {
        // "Lobby" is for *users* sending chat messages
        lobbyClient = ChatClient("Lobby", "", ChatType.LOBBY, "/achat ")
        chatClients.add(lobbyClient)
        // "Game" servers to store messages sent by the server itself.
        gameClient = ChatClient("Game", "", ChatType.GAME, "/achat ") 
        chatClients.add(gameClient)

        // custom chat clients
        chatClients.add(ChatClient("Guild", "Guild >", ChatType.PUBLIC, "/gchat "))
        chatClients.add(ChatClient("Party", "Party >", ChatType.PUBLIC, "/pchat "))
        chatClients.add(ChatClient("Officer", "Officer >", ChatType.PUBLIC, "/oc "))
        chatClients.add(ChatClient("Co-op", "Co-op >", ChatType.PUBLIC, "/cc "))
        // Add new ones here
        // chatClients.add(ChatClient("Tab Title", "Prefix already applied to this chat ingame", ChatType.PUBLIC, "/commandToSendMessageInThisChannel "))
        // ChatType.PUBLIC is a server-wide chat involving more than one person.

        // this just shows me debug info
        // stick your name here if you're working on this
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
            val message = ChatMessage(e.message)
            if (message.plaintext.startsWith("To ") || message.plaintext.startsWith("From "))
            {
                val withUser = message.plaintext.split(' ')[2].dropLast(1)
                if (withUser == "Be") // "To leave Bed Wars, type /lobby". Maybe we could do this by checking the text color?
                    return
                handleDM(withUser, message.htmlFormattedString)
            }
            else
            {
                for (client in chatClients)
                {
                    if (client.type == ChatType.PUBLIC && client.shouldHandleChat(message)) // already got dms, game == catchall
                    {
                        WindowLayoutCoordinator.displayLineFromContext(client.context, client.removePrefix(message.htmlFormattedString))
                        return
                    }
                }
                // if we've made it this far, the message qualified for no other chat clients
                if (message.plaintext.contains(": "))
                    WindowLayoutCoordinator.displayLineFromContext(lobbyClient.context, message.htmlFormattedString)
                else
                    WindowLayoutCoordinator.displayLineFromContext(gameClient.context, message.htmlFormattedString)

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
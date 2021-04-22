package dev.phoenix.chat.server

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import dev.phoenix.chat.chat.ChatMessage
import dev.phoenix.chat.server.chat.ChatClient
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.server.hypixel.FriendTracker
import dev.phoenix.chat.ui.WindowLayoutCoordinator
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import tv.twitch.chat.Chat
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection


/**
 * Hypixel Server implementation.
 */
class Hypixel(client: EntityPlayerSP, server: ServerData) : Server(client, server) {
    var formattedPlayerName = ""
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
        chatClients.add(ChatClient("Blocked", "Blocked >", ChatType.PUBLIC, "/donotuse"))
        // Add new ones here
        // chatClients.add(ChatClient("Tab Title", "Prefix already applied to this chat ingame", ChatType.PUBLIC, "/commandToSendMessageInThisChannel "))
        // ChatType.PUBLIC is a server-wide chat involving more than one person.

        // this just shows me debug info
        // stick your name here if you're working on this
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "/dontusethis", ChatType.PUBLIC, "/dontusethis"))
        }
/*
        val url = URL("https://api.slothpixel.me/api/players/${client.name}")
        val request: URLConnection = url.openConnection()
        request.connect()
        val jp = JsonParser()
        val root: JsonElement =
            jp.parse(InputStreamReader(request.getContent() as InputStream))
        val rootobj = root.asJsonObject
        val prefix = rootobj["rank_formatted"].asString
*/
        formattedPlayerName = "${client.name}"

        registerChatClients()

        //FriendTracker.updateFriendsList()

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
            if (dev.phoenix.chat.chat.ChatFilter.isBegging(message.plaintext) >= 8)
            {
                e.isCanceled=true
                WindowLayoutCoordinator.displayLineFromContext(chatClientMap["Blocked"]!!.context, message.htmlFormattedString + " -(" + dev.phoenix.chat.chat.ChatFilter.isBegging(message.plaintext))
            }
            if (message.plaintext.startsWith("{\"") && message.plaintext.contains("server"))
                return; // /locraw command, just ignore it
            if (message.ampFormatted.contains("&dTo") || message.ampFormatted.contains("&dFrom"))
            {
                val withUser = message.playerName
                handleDM(withUser, message.htmlFormattedString)
            }
            for (client in chatClients)
            {
                if (client.type != ChatType.LOBBY && client.type != ChatType.GAME && client.shouldHandleChat(message)) // already got dms, game == catchall
                {
                    if (client.type == ChatType.PRIVATE)
                    {
                        val content = message.ampFormatted.substringAfter(':')
                        var playerName = "Having fun digging through the source code? ;)"
                        if (message.ampFormatted.contains("&dTo"))
                        {
                            playerName = formattedPlayerName
                        }
                        else
                        {
                            playerName = message.ampFormatted.replaceFirst("&dFrom ", "").substringBefore(':')
                        }
                        WindowLayoutCoordinator.displayLineFromContext(client.context, ChatMessage.renderAmpFormattedAsHTML("$playerName&f&r&f: $content"))
                    }
                    else
                        WindowLayoutCoordinator.displayLineFromContext(client.context, client.removePrefix(message.htmlFormattedString))
                    return
                }
            }
            // if we've made it this far, the message qualified for no other chat clients
            if (message.ampFormatted.contains("&f&r&f: ") || message.ampFormatted.contains("&7&r&7: "))
                WindowLayoutCoordinator.displayLineFromContext(lobbyClient.context, message.htmlFormattedString)
            else
                WindowLayoutCoordinator.displayLineFromContext(gameClient.context, message.htmlFormattedString)


        }
    }

    fun handleDM(with: String, message: String)
    {
        if (!chatClientMap.containsKey(with))
        {
            val newChatClient = ChatClient(with, "From ", ChatType.PRIVATE, "/msg $with ")
            chatClients.add(newChatClient)
            chatClientMap[with] = newChatClient
            //chatClientMap[with]?.let { WindowLayoutCoordinator.displayLineFromContext(it.context, message) }
        }
    }
}
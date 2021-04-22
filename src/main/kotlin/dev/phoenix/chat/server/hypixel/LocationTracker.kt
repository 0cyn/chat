package dev.phoenix.chat.server.hypixel

import com.google.gson.Gson
import dev.phoenix.chat.chat.ChatMessage
import dev.phoenix.chat.mod.Client
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.ui.WindowLayoutCoordinator
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

object LocationTracker {

    var location: ServerType = ServerType.MAIN
    var serverName: String = ""
    var waiting: Boolean = false

    @SubscribeEvent
    fun onChat(e: ClientChatReceivedEvent) {
        if (e.type.toInt() == 0) {
            val message = ChatMessage(e.message)
            if (message.plaintext.startsWith("{\"") && message.plaintext.contains("server"))
            {
                updateLocation(message)
                e.isCanceled = true
                MinecraftForge.EVENT_BUS.unregister(LocationTracker)
                waiting = false
            }
        }
    }

    fun updateLocation(locraw: ChatMessage)
    {
        val dict = locraw.plaintext
        val gson = Gson()
        var map: Map<String, String> = HashMap()
        map = gson.fromJson(dict, map.javaClass)
        // {"server":"lobby26","gametype":"MAIN"}
        if (map.containsKey("gametype"))
        {
            location = map["gametype"]?.let { ServerType.valueOf(it) }!!
        }
        else {
            location = ServerType.UNKNOWN
        }
        if (map.containsKey("server"))
        {
            serverName = map["server"].toString()
        }
        else {
            serverName = ""
        }

        Client.currentServer?.lobbyClient?.context?.let {
            WindowLayoutCoordinator.displayLineFromContext(it, ChatMessage.renderAmpFormattedAsHTML("&a&lJoined &9&l" + serverName + " &7&l("+ location.cleanName + ")"))
        }
    }
}
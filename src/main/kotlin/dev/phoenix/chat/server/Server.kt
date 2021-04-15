package dev.phoenix.chat.server

import dev.phoenix.chat.server.chat.ChatClient
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.common.MinecraftForge
import java.util.*

open class Server(protected var client: EntityPlayerSP, protected var server: ServerData) {
    var lobbyClient: ChatClient = ChatClient("Lobby", "", ChatType.LOBBY, "")
    var chatClients = ArrayList<ChatClient>()
    var chatClientMap: MutableMap<String, ChatClient> = HashMap()

    open fun configureChatClients() {
        chatClients.add(lobbyClient)
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "", ChatType.PUBLIC, ""))
        }
        registerChatClients()
    }

    fun destroy() {
        unregisterChatClients()
        WindowLayoutCoordinator.instance.frame.destroyTabs()
    }

    protected fun registerChatClients() {
        for (client in chatClients) {
            chatClientMap[client.context.title] = client
        }
    }

    open fun unregisterChatClients() {
        for (client in chatClients) {
            MinecraftForge.EVENT_BUS.unregister(client)
        }
    }

    fun sendChatFromTab(tabName: String?, message: String?) {
        chatClientMap[tabName]!!.sendMessageToServer(message!!)
    }
}
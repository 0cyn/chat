package dev.phoenix.chat.server

import dev.phoenix.chat.server.chat.ChatClient
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatType
import dev.phoenix.chat.window.WindowLayoutCoordinator
import net.minecraftforge.common.MinecraftForge
import java.util.*

/**
 * Base server class
 */
open class Server(protected var client: EntityPlayerSP, protected var server: ServerData) {
    var lobbyClient: ChatClient = ChatClient("Lobby", "", ChatType.LOBBY, "")
    var gameClient: ChatClient = ChatClient("Game", "", ChatType.GAME, "")
    var chatClients = ArrayList<ChatClient>()
    var chatClientMap: MutableMap<String, ChatClient> = HashMap()

    open fun configureChatClients() {
        // TODO: this whole thing isn't functional on any server other than hypixel rn, need to fix before upload.
        chatClients.add(lobbyClient)
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "", ChatType.PUBLIC, ""))
        }
        registerChatClients()
    }

    fun destroy() {
        unregisterChatClients()
        WindowLayoutCoordinator.frame.destroyTabs()
    }

    protected fun registerChatClients() {
        for (client in chatClients) {
            chatClientMap[client.context.title] = client
        }
        WindowLayoutCoordinator.displayLineFromContext(lobbyClient.context, "Phoenix Chat Manager")
        WindowLayoutCoordinator.displayLineFromContext(lobbyClient.context, "written by _kritanta")
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
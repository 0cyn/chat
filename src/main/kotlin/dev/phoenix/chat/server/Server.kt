package dev.phoenix.chat.server

import dev.phoenix.chat.server.chat.ChatClient
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.ServerData
import dev.phoenix.chat.server.chat.ChatType
import net.minecraftforge.common.MinecraftForge
import java.util.*

open class Server(protected var client: EntityPlayerSP, protected var server: ServerData) {
    var chatClients = ArrayList<ChatClient>()
    var chatClientMap: MutableMap<String, ChatClient> = HashMap()
    open fun configureChatClients() {
        chatClients.add(ChatClient("Lobby", "", ChatType.PUBLIC, ""))
        if (client.name.contains("_kritanta")) {
            chatClients.add(ChatClient("Debug", "", ChatType.PUBLIC, ""))
        }
        registerChatClients()
    }

    protected fun registerChatClients() {
        for (client in chatClients) {
            chatClientMap[client.context.title] = client
            MinecraftForge.EVENT_BUS.register(client)
        }
    }

    fun sendChatFromTab(tabName: String?, message: String?) {
        chatClientMap[tabName]!!.sendMessageToServer(message!!)
    }
}
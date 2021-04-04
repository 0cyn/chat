package dev.phoenix.chat

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraft.client.Minecraft
import dev.phoenix.chat.server.Hypixel
import dev.phoenix.chat.server.Server
import net.minecraft.client.entity.EntityOtherPlayerMP

class Client() {
    var currentServer: Server? = null
    var player: EntityPlayerSP? = null
    fun sendChat(string: String?) {
        player!!.sendChatMessage(string)
    }

    @SubscribeEvent
    fun entityJoinWorld(event: EntityJoinWorldEvent) {
        if (event.entity is EntityPlayerSP) {
            if (currentServer != null) return  //TODO THIS PROPERLY
            val player = event.entity as EntityPlayerSP
            val serverData = Minecraft.getMinecraft().currentServerData
            currentServer = if (serverData != null) {
                println(String.format("Player joined server with ip <%s>", serverData.serverIP))
                if (serverData.serverIP.contains("hypixel")) Hypixel(player, serverData) else Server(player, serverData)
            } else {
                return
                // i dont know enough kotlin to make this work
                // TODO someone who knows this language at a basic lvl pls fix
                // player is on local server
                //Hypixel(player, null)
            }
            this.player = player
            currentServer!!.configureChatClients()
        } else if (event.entity is EntityOtherPlayerMP) {
        }
    }

    companion object {
        @JvmStatic
        val instance = Client()
    }
}
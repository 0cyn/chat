package dev.phoenix.chat.mod

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraft.client.Minecraft
import dev.phoenix.chat.server.Hypixel
import dev.phoenix.chat.server.Server
import dev.phoenix.chat.server.hypixel.LocationTracker
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.network.FMLNetworkEvent

/**
 * This represents the local minecraft client. 
 * 
 * It abstracts out interactions at the forge/network level, processes some events, and stores current player/server objects
 */
object Client {
    var currentServer: Server? = null
    var player: EntityPlayerSP? = null
    var world: World? = null

    fun sendChat(string: String?) {
        player!!.sendChatMessage(string)
    }

    @SubscribeEvent
    fun clientConnectedToServer(event: FMLNetworkEvent.ClientConnectedToServerEvent) {
        currentServer?.destroy()
        currentServer = null
        player = null
    }

    @SubscribeEvent
    fun entityJoinWorld(event: EntityJoinWorldEvent) {
        if (event.entity is EntityPlayerSP) {

            if (currentServer != null) {
                if (currentServer is Hypixel)
                {
                    if (LocationTracker.waiting)
                        return

                    MinecraftForge.EVENT_BUS.register(LocationTracker)
                    LocationTracker.waiting = true
                    sendChat("/locraw")
                    world = event.world
                }
                return
            }

            val player = event.entity as EntityPlayerSP
            val serverData = Minecraft.getMinecraft().currentServerData

            currentServer = if (serverData != null) {
                Chat.logDebug(String.format("Player joined server with ip <%s>", serverData.serverIP))
                if (serverData.serverIP.contains("hypixel")) Hypixel(player, serverData) else Server(player, serverData)
            } else {
                return
                // TODO set up some sort of local server chat UI
            }

            Client.player = player
            currentServer!!.configureChatClients()

        } else if (event.entity is EntityOtherPlayerMP) {
            return
            // TODO player list
        }
    }
}
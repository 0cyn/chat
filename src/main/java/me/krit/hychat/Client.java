package me.krit.hychat;

import me.krit.hychat.server.Hypixel;
import me.krit.hychat.server.Server;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Client
{
    public Server currentServer;

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerSP) {
            EntityPlayerSP player = (EntityPlayerSP) event.entity;
            ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            if (serverData != null) {
                System.out.println(String.format("Player joined server with ip <%s>", serverData.serverIP));
                if (serverData.serverIP.contains("hypixel"))
                    currentServer = new Hypixel(player, serverData);
                else
                    currentServer = new Server(player, serverData);
            }
            else
            {
                // player is on local server
                currentServer = new Server(player, null);
            }

            currentServer.configureChatClients();
        }
    }
}

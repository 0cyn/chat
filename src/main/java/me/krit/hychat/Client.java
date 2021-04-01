package me.krit.hychat;

import me.krit.hychat.server.Hypixel;
import me.krit.hychat.server.Server;
import me.krit.hychat.window.WindowLayoutCoordinator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Client
{
    public Server currentServer;
    public EntityPlayerSP player;

    private static final Client instance = new Client(null);

    public Client(Server currentServer)
    {
        this.currentServer = currentServer;
    }

    public static Client getInstance() {
        return instance;
    }

    public void sendChat(String string)
    {
        player.sendChatMessage(string);
    }


    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerSP) {
            if (currentServer != null)
                return;
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
                currentServer = new Hypixel(player, null);
            }

            this.player = player;

            currentServer.configureChatClients();
        }
    }
}

package me.krit.hychat.server;

import me.krit.hychat.server.chat.ChatClient;
import me.krit.hychat.server.chat.ChatType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class Server
{

    protected EntityPlayerSP client;
    protected ServerData server;
    protected ArrayList<ChatClient> chatClients = new ArrayList<>();

    public Server(EntityPlayerSP client, ServerData server) {
        this.client = client;
        this.server = server;
    }

    public void configureChatClients()
    {
        chatClients.add(new ChatClient("Lobby", "", ChatType.PUBLIC));
        registerChatClients();
    }

    protected void registerChatClients()
    {
        for (ChatClient client : chatClients)
        {
            MinecraftForge.EVENT_BUS.register(client);
        }
    }

    public void deliverChatMessage(String message) {
        client.sendChatMessage(message);
    }
}

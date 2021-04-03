package me.krit.hychat.server;

import me.krit.hychat.server.chat.ChatClient;
import me.krit.hychat.server.chat.ChatType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server
{

    protected EntityPlayerSP client;
    protected ServerData server;
    protected ArrayList<ChatClient> chatClients = new ArrayList<>();
    protected Map<String, ChatClient> chatClientMap = new HashMap<>();

    public Server(EntityPlayerSP client, ServerData server) {
        this.client = client;
        this.server = server;
    }

    public void configureChatClients()
    {
        chatClients.add(new ChatClient("Lobby", "", ChatType.PUBLIC, ""));
        registerChatClients();
    }

    protected void registerChatClients()
    {
        for (ChatClient client : chatClients)
        {
            chatClientMap.put(client.context.title, client);
            MinecraftForge.EVENT_BUS.register(client);
        }
    }

    public void sendChatFromTab(String tabName, String message)
    {
        chatClientMap.get(tabName).sendMessage(message);
    }
}

package me.krit.hychat.server;


import me.krit.hychat.server.chat.ChatClient;
import me.krit.hychat.server.chat.ChatType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;

public class Hypixel extends Server
{
    public Hypixel(EntityPlayerSP client, ServerData server)
    {
        super(client, server);
    }

    public void configureChatClients()
    {
        chatClients.add(new ChatClient("Lobby", "[", ChatType.PUBLIC, "/achat "));
        chatClients.add(new ChatClient("Guild", "Guild >", ChatType.PUBLIC,"/gchat "));
        chatClients.add(new ChatClient("Party", "Party >", ChatType.PUBLIC, "/pchat "));

        registerChatClients();
    }
}

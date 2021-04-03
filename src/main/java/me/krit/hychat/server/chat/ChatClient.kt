package me.krit.hychat.server.chat;

import me.krit.hychat.Client;
import me.krit.hychat.window.WindowLayoutCoordinator;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatClient
{
    public ChatContext context;

    public ChatClient(ChatContext context)
    {
        this.context = context;
        System.out.println("Registered ChatClient ".concat(context.title));
        WindowLayoutCoordinator.getInstance().createTabForChatContext(context);
    }

    public ChatClient(String title, String chatPrefix, ChatType type, String sendToContextCommand){
        this.context = new ChatContext(title, chatPrefix, type, sendToContextCommand);
        System.out.println("Registered ChatClient ".concat(context.title));
        WindowLayoutCoordinator.getInstance().createTabForChatContext(context);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e)
    {
        if (e.type == 0)
        {
            String message = e.message.getUnformattedText();
            //System.out.print("Chat Client ".concat(context.title).concat(" Recieved \"").concat(message).concat("\""));
            if (context.messageQualifiesForContext(message.replaceAll("\\u00A7.", "")))
                WindowLayoutCoordinator.getInstance().displayLineFromContext(context, message);
        }
    }

    public void sendMessage(String message)
    {
        message = context.sendToContextCommand + message;
        Client.getInstance().sendChat(message);
    }

}

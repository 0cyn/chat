package me.krit.hychat.observers;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatObserver
{
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e)
    {
        if (e.type == 0)
        {
            String message = e.message.getUnformattedText();
        }
    }
}

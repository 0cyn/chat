package me.krit.hychat.window;

import me.krit.hychat.server.chat.ChatContext;
import me.krit.hychat.ui.HyChatFrame;

import java.util.HashMap;

public class WindowLayoutCoordinator
{
    private static final WindowLayoutCoordinator instance = new WindowLayoutCoordinator();
    private HyChatFrame frame;

    private WindowLayoutCoordinator()
    {
        frame = new HyChatFrame();
    }

    public static WindowLayoutCoordinator getInstance()
    {
        return instance;
    }

    public HyChatFrame getFrame()
    {
        return frame;
    }

    public void createTabForChatContext(ChatContext context)
    {
        frame.createTabWithName(context.title);
    }

    public void displayLineFromContext(ChatContext context, String message)
    {
        frame.addLineToTabWithName(context.title, message.concat("\n"));
    }
}

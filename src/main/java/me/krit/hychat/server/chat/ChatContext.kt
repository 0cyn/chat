package me.krit.hychat.server.chat;

public class ChatContext
{
    public String title;
    public String chatPrefix;
    public ChatType type;
    public String sendToContextCommand;

    public ChatContext(String title, String chatPrefix, ChatType type, String sendToContextCommand)
    {
        this.title = title;
        this.chatPrefix = chatPrefix;
        this.type = type;
        this.sendToContextCommand = sendToContextCommand;
    }

    public boolean messageQualifiesForContext(String message)
    {
        System.out.println(message);
        return message.startsWith(chatPrefix);
    }
}

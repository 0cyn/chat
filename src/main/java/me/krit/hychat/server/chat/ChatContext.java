package me.krit.hychat.server.chat;

public class ChatContext
{
    public String title;
    private String chatPrefix;
    public ChatType type;

    public ChatContext(String title, String chatPrefix, ChatType type)
    {
        this.title = title;
        this.chatPrefix = chatPrefix;
        this.type = type;
    }

    public boolean messageQualifiesForContext(String message)
    {
        return message.startsWith(chatPrefix);
    }
}

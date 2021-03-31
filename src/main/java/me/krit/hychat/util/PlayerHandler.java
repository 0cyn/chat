package me.krit.hychat.util;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerHandler
{
    public static Color getPlayerColor(String playerName)
    {
        playerName = playerName.toLowerCase().trim();
        if (!playerName.contains("[admin") && !playerName.contains("[mcprohost"))
        {
            if (!playerName.contains("[mod") && !playerName.contains("[vip") && !playerName.contains("guild"))
            {
                if (playerName.contains("[mvp++"))
                {
                    return Color.orange;
                } else if (!playerName.contains("[helper") && !playerName.contains("[mvp") && !playerName.contains("build team") && !playerName.contains("party"))
                {
                    return playerName.contains("[youtube]") ? Color.red : Color.gray;
                } else
                {
                    return Color.blue;
                }
            } else
            {
                return new Color(19, 105, 4);
            }
        } else
        {
            return Color.red;
        }
    }

    public static String getPlayerName(String tabName)
    {
        String[] split = tabName.split(" ");
        return split.length > 1 ? split[1] : split[0];
    }

    public static String getPlayerTabName(String fullMessage)
    {
        Pattern p = Pattern.compile("((\\[[\\w]+\\+{0,2}\\]\\s){0,1}[\\w\\d]+):");
        Matcher m = p.matcher(fullMessage);
        boolean found = m.find();
        String pName = m.group(1);
        return pName;
    }
}

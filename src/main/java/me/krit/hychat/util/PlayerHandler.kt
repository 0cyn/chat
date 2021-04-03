package me.krit.hychat.util

import java.awt.Color
import java.util.regex.Pattern

object PlayerHandler {
    fun getPlayerColor(playerName: String): Color {
        var playerName = playerName
        playerName = playerName.toLowerCase().trim { it <= ' ' }
        return if (!playerName.contains("[admin") && !playerName.contains("[mcprohost")) {
            if (!playerName.contains("[mod") && !playerName.contains("[vip") && !playerName.contains("guild")) {
                if (playerName.contains("[mvp++")) {
                    Color.orange
                } else if (!playerName.contains("[helper") && !playerName.contains("[mvp") && !playerName.contains("build team") && !playerName.contains("party")) {
                    if (playerName.contains("[youtube]")) Color.red else Color.gray
                } else {
                    Color.blue
                }
            } else {
                Color(19, 105, 4)
            }
        } else {
            Color.red
        }
    }

    fun getPlayerName(tabName: String): String {
        val split = tabName.split(" ".toRegex()).toTypedArray()
        return if (split.size > 1) split[1] else split[0]
    }

    fun getPlayerTabName(fullMessage: String?): String {
        val p = Pattern.compile("((\\[[\\w]+\\+{0,2}\\]\\s){0,1}[\\w\\d]+):")
        val m = p.matcher(fullMessage)
        val found = m.find()
        return m.group(1)
    }
}
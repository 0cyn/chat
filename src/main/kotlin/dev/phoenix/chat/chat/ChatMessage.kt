package dev.phoenix.chat.chat

import dev.phoenix.chat.server.hypixel.LocationTracker
import dev.phoenix.chat.server.hypixel.ServerType
import net.minecraft.util.IChatComponent
import javax.xml.stream.Location

/**
 * Abstracts the ITextComponent and performs some formatting on it
 */
class ChatMessage(val message: IChatComponent) {
    val plaintext: String = message.unformattedText.replace("\\u00A7.".toRegex(), "")
    val ampFormatted: String = message.formattedText.replace("\\u00A7".toRegex(), "&")
    val formatted: String = message.formattedText
    val playerName: String = findPlayerName(message.unformattedText.replace("\\u00A7.".toRegex(), ""))
    var htmlFormattedString: String = renderFormattedStringAsHTML(message.formattedText)
    val isFromPlayer: Boolean = isPlayerMessage(message.formattedText)

    companion object {

        private fun findPlayerName(plaintextMsg: String): String {
            return plaintextMsg.split(':')[0].split(' ').last()
        }

        private fun isPlayerMessage(formatted: String): Boolean {
            val ampf = formatted.replace("\\u00A7".toRegex(), "&")
            return ampf.contains("&f&r&f: ") || ampf.contains("&7&r&7: ") || ampf.contains("&r&f:") ||  ampf.contains("&r&7:")
        }

        fun renderAmpFormattedAsHTML(message: String): String {
            return renderFormattedStringAsHTML(message.replace("&", "§"))
        }

        private fun renderFormattedStringAsHTML(message: String): String {

            // I did this like I would have in a procedural language
            // I'm not sure if i'm comfortable with the overhead of an OOP approach,
            //      however an OOP approach would provide much more maintainable code

            val formatted = StringBuilder()         

            // Closer keeps track of all of the tags we need to close
            val closer: MutableMap<String, Int?> = mutableMapOf("span" to 0, "B" to 0, "U" to 0, "STRIKE" to 0, "I" to 0)

            // keeps track of index in original string
            var i = 0                               
            var skip = false                        

            for (token in message.toCharArray()) {
                if (token == '\u00A7') {            // "Section Symbol", denotes a color code in vanilla minecraft.
                    
                    skip = true
                    val color = message.toCharArray()[i + 1]

                    var tagType = ""
                    var tagColor = ""

                    when (color) {
                        '0' -> {
                            tagType = "span"
                            tagColor = "#000000"
                        }
                        '1' -> {
                            tagType = "span"
                            tagColor = "#0000AA"
                        }
                        '2' -> {
                            tagType = "span"
                            tagColor = "#00AA00"
                        }
                        '3' -> {
                            tagType = "span"
                            tagColor = "#00AAAA"
                        }
                        '4' -> {
                            tagType = "span"
                            tagColor = "#AA0000"
                        }
                        '5' -> {
                            tagType = "span"
                            tagColor = "#AA00AA"
                        }
                        '6' -> {
                            tagType = "span"
                            tagColor = "#FFAA00"
                        }
                        '7' -> {
                            tagType = "span"
                            tagColor = "#AAAAAA"
                        }
                        '8' -> {
                            tagType = "span"
                            tagColor = "#555555"
                        }
                        '9' -> {
                            tagType = "span"
                            tagColor = "#5555FF"
                        }
                        'a' -> {
                            tagType = "span"
                            tagColor = "#55FF55"
                        }
                        'b' -> {
                            tagType = "span"
                            tagColor = "#55FFFF"
                        }
                        'c' -> {
                            tagType = "span"
                            tagColor = "#FF5555"
                        }
                        'd' -> {
                            tagType = "span"
                            tagColor = "#FF55FF"
                        }
                        'e' -> {
                            tagType = "span"
                            tagColor = "#FFFF55"
                        }
                        'f' -> {
                            tagType = "span"
                            tagColor = "#FFFFFF"
                        }
                        'l' -> tagType = "B"
                        'm' -> tagType = "STRIKE"
                        'n' -> tagType = "U"
                        'o' -> tagType = "I"
                        'r' -> tagType = "RESET"
                        else -> tagType = "" // just covers &k (obfuscated) which i'm not sure how to tackle yet
                    }
                    
                    if (tagType == "")
                        continue

                    if (tagType == "RESET")
                    {
                        // close up literally every other tag we've used.
                        for ((k, v) in closer)
                        {
                            var j = v
                            if (j != null) {
                                while (j > 0) {
                                    formatted.append("</$k>")
                                    --j
                                }
                            }
                        }

                        // reset to white
                        tagType = "span"
                        tagColor = "#FFFFFF"
                    }
                    
                    if (closer[tagType]!! > 0) {
                        formatted.append("</$tagType>")
                        var left: Int? = closer[tagType]
                        if (left != null) {
                            left -= 1
                        }
                        closer[tagType] = left
                    }

                    if (tagType == "span")
                        formatted.append("<span style=\"color:$tagColor\">")
                    else
                        formatted.append("<$tagType>")

                    var left: Int? = closer[tagType]
                    if (left != null) {
                        left += 1
                    }
                    closer[tagType] = left
                    
                } 
                else {                                // not a section character
                    if (skip)                         // if it came directly after a section character, skip
                        skip = false 
                    else {
                        when (token) { // we need to escape these for html
                            '<' -> formatted.append("&lt;")
                            '>' -> formatted.append("&gt;")
                            '&' -> formatted.append("&amp;")
                            else ->  // just a normal character, then
                                formatted.append(token)
                        }
                    }
                }
                i++
            }

            return formatted.toString()
            
        }
    }

}

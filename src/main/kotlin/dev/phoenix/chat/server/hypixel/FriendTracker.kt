package dev.phoenix.chat.server.hypixel

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import dev.phoenix.chat.chat.ChatMessage
import dev.phoenix.chat.mod.Chat
import dev.phoenix.chat.mod.Client
import dev.phoenix.chat.ui.WindowLayoutCoordinator
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Window
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection

object FriendTracker {

    // Process friends joining/leaving so we dont have to hit slothpixel more than once
    @SubscribeEvent
    fun onChat(e: ClientChatReceivedEvent) {
        if (e.type.toInt() == 0) {
            val message = ChatMessage(e.message)
        }
    }

    fun updateFriendsList()
    {
        val url = URL("https://api.slothpixel.me/api/players/${Client.player?.name}/friends")
        val request: URLConnection = url.openConnection()
        request.connect()
        val jp = JsonParser()
        val root: JsonElement =
            jp.parse(InputStreamReader(request.getContent() as InputStream))
        val rootobj = root.asJsonArray
        for (friend in rootobj)
        {
            val friendUUID: String = friend.asJsonObject.get("uuid").asString
            val furl = URL("https://api.slothpixel.me/api/players/$friendUUID")
            val frequest: URLConnection = furl.openConnection()
            frequest.connect()
            val fjp = JsonParser()
            val froot: JsonElement =
                fjp.parse(InputStreamReader(frequest.getContent() as InputStream))
            val frootobj = froot.asJsonObject
            val prefix: String = frootobj["rank_formatted"].asString
            val username: String = frootobj["username"].asString
            if (frootobj["online"].asBoolean) {
                val formattedPlayerName = "&a● $prefix $username"
                WindowLayoutCoordinator.frame.plistModel.addElement(
                    "<html>" + ChatMessage.renderAmpFormattedAsHTML(
                        formattedPlayerName
                    ) + "</html>"
                )
            }
        }
    }
}
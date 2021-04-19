# Source Map

## /chat

ChatClient.kt - Represents the backend of a "Tab"/"Channel" (e.g. "Lobby")  
ChatContext.kt - Object in ChatClient that holds the important values and checks some things on them  
ChatMessage.kt - Abstracts out the ITextComponent minecraft class, converts it into several formats we need  
ChatType.kt - Enum that represents different types of chat channels  

## /mod

Chat.kt - Mod object and shared general instance  
Client.kt - Object that abstracts network level client interactions, catches events, and stores server/player objects  
KotlinAdapter.kt - Kotlin Adapter for Forge  
Settings - Unimplemented config abstraction  

## /server

Server.kt - Represents the server the client is connected to  
Hypixel.kt - Server Implementation specifically for Hypixel  

## /ui

ChatFrame.kt - This is the main Frame/Window we have. Creates the window and the tabbed pane inside it  
MotionPanel.kt - Unimplemented class  
TabCloseCallbackHolder.java - Certain code in our project requires a java specific object. So this holds that.  
TabPanel.kt - This represents a single tab within the pane. It's just a JPanel with all our fun stuff crammed in.  
WindowLayoutCoordinator.kt - object that coordinates window layout (surprise)  

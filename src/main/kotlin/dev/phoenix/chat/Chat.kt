package dev.phoenix.chat

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.common.MinecraftForge
import dev.phoenix.chat.window.WindowLayoutCoordinator
import dev.phoenix.chat.KotlinAdapter
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod

@Mod(modid = "Chat", version = "1.0.0", acceptedMinecraftVersions = "[1.8,1.8.9]", modLanguage = "kotlin", modLanguageAdapter = "dev.phoenix.chat.KotlinAdapter")
object Chat {
    @Mod.EventHandler
    fun preinit(e: FMLPreInitializationEvent) {
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(Client.instance)
        MinecraftForge.EVENT_BUS.register(WindowLayoutCoordinator.instance.frame)
    }

}
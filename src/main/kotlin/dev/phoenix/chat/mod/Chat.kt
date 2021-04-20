package dev.phoenix.chat.mod

import dev.phoenix.chat.mod.Client
import dev.phoenix.chat.ui.WindowLayoutCoordinator
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import javax.swing.JFrame

/**
 * Mod object and global generic object we can reference
 */
@Mod(modid = "Chat", version = "1.0.0", acceptedMinecraftVersions = "[1.8,1.8.9]", modLanguage = "kotlin", modLanguageAdapter = "dev.phoenix.chat.mod.KotlinAdapter")
object Chat {

    var config: Configuration? = null

    @Mod.EventHandler
    fun preinit(e: FMLPreInitializationEvent) {
        //config = Configuration(e.suggestedConfigurationFile)
        //config!!.load()
        WindowLayoutCoordinator.frame
    }

    fun setConfigValue(value: String?, option: Boolean) {
        config!!["general", value, option].set(option)
        config!!.save()
    }

    fun setConfigValue(value: String?, option: String?) {
        config!!["general", value, option].set(option)
        config!!.save()
    }

    fun logDebug(msg: String) {
        return
        // TODO write this
    }


    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(Client)
        MinecraftForge.EVENT_BUS.register(WindowLayoutCoordinator.frame)
    }

}
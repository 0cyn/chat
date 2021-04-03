package me.krit.hychat

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.common.MinecraftForge
import me.krit.hychat.window.WindowLayoutCoordinator
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod

@Mod(modid = "HyChat", version = "1.0.0", acceptedMinecraftVersions = "[1.8,1.8.9]")
class HyChat {
    @Mod.EventHandler
    fun preinit(e: FMLPreInitializationEvent) {
        config = Configuration(e.suggestedConfigurationFile)
        config!!.load()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(client)
        MinecraftForge.EVENT_BUS.register(WindowLayoutCoordinator.instance.frame)
    }

    companion object {
        const val MODID = "HyChat"
        const val VERSION = "1.0.0"
        var config: Configuration? = null
        var windowController: WindowLayoutCoordinator? = null
        var client = Client.instance
        fun setConfigValue(value: String?, option: Boolean) {
            val var10001 = config
            config!!["general", value, option].set(option)
            config!!.save()
        }

        fun setConfigValue(value: String?, option: String?) {
            val var10001 = config
            config!!["general", value, option].set(option)
            config!!.save()
        }
    }
}
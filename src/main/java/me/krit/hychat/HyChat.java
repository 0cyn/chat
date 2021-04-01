package me.krit.hychat;

import com.formdev.flatlaf.FlatDarkLaf;
import me.krit.hychat.window.WindowLayoutCoordinator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.swing.*;

@Mod(
        modid = "HyChat",
        version = "1.0.0",
        acceptedMinecraftVersions = "[1.8,1.8.9]"
)
public class HyChat
{
    public static final String MODID = "HyChat";
    public static final String VERSION = "1.0.0";
    public static Configuration config;
    public static WindowLayoutCoordinator windowController;
    public static Client client = Client.getInstance();


    @EventHandler
    public void preinit(FMLPreInitializationEvent e)
    {
        config = new Configuration(e.getSuggestedConfigurationFile());
        config.load();

    }

    public static void setConfigValue(String value, boolean option)
    {
        Configuration var10001 = config;
        config.get("general", value, option).set(option);
        config.save();
    }

    public static void setConfigValue(String value, String option)
    {
        Configuration var10001 = config;
        config.get("general", value, option).set(option);
        config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(client);
        MinecraftForge.EVENT_BUS.register(WindowLayoutCoordinator.getInstance().getFrame());
    }
}

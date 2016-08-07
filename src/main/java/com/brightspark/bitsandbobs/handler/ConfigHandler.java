package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler
{
    public static class Categories
    {
        public static final String GENERAL = Configuration.CATEGORY_GENERAL;
    }

    public static Configuration configuration;

    public static void init(File configFile)
    {
        if(configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        Config.healingBlockValidFuel = configuration.getStringList("healingBlockValidFuel", Categories.GENERAL, Config.healingBlockValidFuel, "A list of all item IDs which are valid to be used as fuel in the healing block. Put all metadata for an id in the next entry in the array with individual metadata separated by commas (See default for example).");

        if(configuration.hasChanged())
            configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equalsIgnoreCase(Reference.MOD_ID))
            //Resync configs
            loadConfiguration();
    }
}

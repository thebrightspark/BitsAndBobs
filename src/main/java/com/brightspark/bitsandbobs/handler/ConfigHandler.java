package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        Config.mirageOrbCooldown = configuration.getInt("mirageOrbCooldown", Categories.GENERAL, Config.mirageOrbCooldown, 0, Integer.MAX_VALUE, "The Mirage Orb's cooldown in seconds");
        Config.mirageOrbGhostLife = configuration.getInt("mirageOrbGhostLife", Categories.GENERAL, Config.mirageOrbGhostLife, 1, Integer.MAX_VALUE, "The Mirage Orb ghost's life in seconds");

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

    public static void getHealingBlockInputs()
    {
        //Create mapping for valid healing block items with fuel values
        for(int i = 0; i < Config.healingBlockValidFuel.length; i++)
        {
            String[] entry = Config.healingBlockValidFuel[i].split(",");
            //2 values -> Item ID and fuel value
            if(entry.length == 2)
            {
                Item item = Item.getByNameOrId(entry[0]);
                if(item == null) continue;
                int value = -1;
                try
                {
                    value = Integer.parseInt(entry[1]);
                }
                catch(NumberFormatException e) {}
                if(value < 1) continue;
                Config.healingBlockValidFuelStacks.put(new ItemStack(item), value);
            }
            //3 values -> Item ID, Item metadata and fuel value
            else if(entry.length == 3)
            {
                Item item = Item.getByNameOrId(entry[0]);
                if(item == null) continue;
                int meta = -1;
                int value = -1;
                try
                {
                    meta = Integer.parseInt(entry[1]);
                    value = Integer.parseInt(entry[2]);
                }
                catch(NumberFormatException e) {}
                if(meta < 0 || value < 1) continue;
                Config.healingBlockValidFuelStacks.put(new ItemStack(item, 1, meta), value);
            }
        }
        //LogHelper.info("Generated ItemStack map: (" + Config.healingBlockValidFuelStacks.size() + " values)\n" + Config.healingBlockValidFuelStacks.toString());
    }
}

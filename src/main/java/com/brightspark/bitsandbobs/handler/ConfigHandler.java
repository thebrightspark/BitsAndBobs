package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler
{
    public static class Categories
    {
        public static final String GENERAL = Configuration.CATEGORY_GENERAL;
    }

    public static Configuration config;
    private static List<String> propOrder = new ArrayList<String>();

    public static void init(File configFile)
    {
        if(config == null)
        {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static String[] getStringList(String key, String[] defaultValue, String comment)
    {
        Property prop = config.get(Categories.GENERAL, key, defaultValue, comment);
        setPropDefaults(prop, key);
        return prop.getStringList();
    }

    private static String getString(String key, String defaultValue, String comment)
    {
        Property prop = config.get(Categories.GENERAL, key, defaultValue, comment);
        setPropDefaults(prop, key);
        return prop.getString();
    }

    private static int getInt(String key, int defaultValue, int min, int max, String comment)
    {
        Property prop = config.get(Categories.GENERAL, key, defaultValue, comment).setMinValue(min).setMaxValue(max);
        setPropDefaults(prop, key);
        return prop.getInt();
    }

    private static void setPropDefaults(Property prop, String key)
    {
        prop.setLanguageKey(Reference.MOD_ID + ".config." + key);
        propOrder.add(prop.getName());
    }

    private static void loadConfiguration()
    {
        propOrder.clear();

        Config.healingBlockValidFuel = getStringList("healingBlockValidFuel", Config.healingBlockValidFuel, "A list of all item IDs which are valid to be used as fuel in the healing block. Put all metadata for an id in the next entry in the array with individual metadata separated by commas (See default for example).");
        Config.mirageOrbCooldown = getInt("mirageOrbCooldown", Config.mirageOrbCooldown, 0, Integer.MAX_VALUE, "The Mirage Orb's cooldown in seconds");
        Config.mirageOrbGhostLife = getInt("mirageOrbGhostLife", Config.mirageOrbGhostLife, 1, Integer.MAX_VALUE, "The Mirage Orb ghost's life in seconds");

        Config.commandKillName = getString("commandKillName", Config.commandKillName, "Name for the kill command - configurable in-case of conflicting command names");
        Config.commandKillMessages = getStringList("commandKillMessages", Config.commandKillMessages, "Kill command result messages. Use one '%s' somewhere in the message to insert the kill count there.");

        config.setCategoryPropertyOrder(Categories.GENERAL, propOrder);

        if(config.hasChanged())
            config.save();
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

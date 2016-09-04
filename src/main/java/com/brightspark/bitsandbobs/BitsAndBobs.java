package com.brightspark.bitsandbobs;

import com.brightspark.bitsandbobs.gui.GuiHandler;
import com.brightspark.bitsandbobs.handler.ConfigHandler;
import com.brightspark.bitsandbobs.handler.EntityEventHandler;
import com.brightspark.bitsandbobs.init.*;
import com.brightspark.bitsandbobs.message.MessageSpawnGhostOnServer;
import com.brightspark.bitsandbobs.message.MessageSetClientGhostData;
import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, dependencies=Reference.DEPEND)
public class BitsAndBobs
{
    @Mod.Instance(Reference.MOD_ID)
    public static BitsAndBobs instance;

    public static final CreativeTabs BAB_TAB = new CreativeTabs(Reference.MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return BABItems.itemLifeStick;
        }

        @Override
        public String getTranslatedTabLabel()
        {
            return Reference.MOD_NAME;
        }
    };

    public static SimpleNetworkWrapper NETWORK;

    static
    {
        //Enable the universal buckets for the fluid xp
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Initialize item, blocks and configs here

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
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

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        NETWORK.registerMessage(MessageSpawnGhostOnServer.Handler.class, MessageSpawnGhostOnServer.class, 0, Side.SERVER);
        NETWORK.registerMessage(MessageSetClientGhostData.Handler.class, MessageSetClientGhostData.class, 1, Side.CLIENT);

        BABItems.init();
        BABBlocks.init();
        if(event.getSide() == Side.CLIENT)
        {
            BABItems.regModels();
            BABBlocks.regModels();
        }
        BABEntities.init(event.getSide() == Side.CLIENT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        //Initialize textures/models, GUIs, tile entities, recipies, event handlers here

        BABRecipes.init();
        BABTileEntities.init();
        //TODO: Uncomment this when I want to try xp juice again...
        //BABFluids.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        //Run stuff after mods have initialized here

        //TODO: Uncomment this when I want to try xp juice again...
        //BABFluids.postInit();
        //if(event.getSide() == Side.CLIENT)
        //    BABFluids.regModels();
    }
}

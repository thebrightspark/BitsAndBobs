package com.brightspark.bitsandbobs;

import com.brightspark.bitsandbobs.command.CommandKill;
import com.brightspark.bitsandbobs.handler.GuiHandler;
import com.brightspark.bitsandbobs.handler.ConfigHandler;
import com.brightspark.bitsandbobs.handler.EntityEventHandler;
import com.brightspark.bitsandbobs.init.*;
import com.brightspark.bitsandbobs.message.MessageSpawnGhostOnServer;
import com.brightspark.bitsandbobs.message.MessageSetClientGhostData;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, dependencies=Reference.DEPEND, guiFactory=Reference.GUI_FACTORY)
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
        ConfigHandler.getHealingBlockInputs();
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        NETWORK.registerMessage(MessageSpawnGhostOnServer.Handler.class, MessageSpawnGhostOnServer.class, 0, Side.SERVER);
        NETWORK.registerMessage(MessageSetClientGhostData.Handler.class, MessageSetClientGhostData.class, 1, Side.CLIENT);

        BABItems.regItems();
        BABBlocks.regBlocks();
        BABEntities.regEntities();
        if(event.getSide() == Side.CLIENT)
        {
            BABItems.regModels();
            BABBlocks.regModels();
            BABEntities.regRenders();
        }
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

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        //Register commands
        event.registerServerCommand(new CommandKill());
    }
}

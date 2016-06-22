package com.brightspark.bitsandbobs;

import com.brightspark.bitsandbobs.gui.GuiHandler;
import com.brightspark.bitsandbobs.init.BABBlocks;
import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.init.BABRecipes;
import com.brightspark.bitsandbobs.init.BABTileEntities;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
public class BitsAndBobs
{
    @Mod.Instance(Reference.MOD_ID)
    public static BitsAndBobs instance;

    public static final CreativeTabs BAB_TAB = new CreativeTabs(Reference.MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return BABItems.lifeStick;
        }

        @Override
        public String getTranslatedTabLabel()
        {
            return Reference.MOD_NAME;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Initialize item, blocks and configs here

        BABItems.init();
        BABBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        //Initialize textures/models, GUIs, tile entities, recipies, event handlers here

        if(event.getSide() == Side.CLIENT)
        {
            BABItems.regModels();
            BABBlocks.regModels();
        }

        BABRecipes.init();
        BABTileEntities.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        //Run stuff after mods have initialized here

    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.reference.Reference;
import com.brightspark.bitsandbobs.util.ClientUtils;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABFluids
{
    public static Fluid fluidXpJuice;
    public static Block blockXpJuice;

    private static ResourceLocation createLoc(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, "blocks/" + name);
    }

    public static void init()
    {
        if(Loader.isModLoaded(Names.ModIds.ENDERIO))
            LogHelper.info("EnderIO found, relying on their Liquid XP Juice.");
        else if(Loader.isModLoaded(Names.ModIds.OPEN_BLOCKS))
            LogHelper.info("OpenBlocks found, relying on their Liquid XP Juice.");
        else
        {
            LogHelper.info("EnderIO or OpenBlocks not found, registering own Liquid XP Juice.");
            FluidRegistry.addBucketForFluid(fluidXpJuice = new Fluid(Names.Fluids.XP_JUICE, createLoc(Names.Fluids.XP_JUICE + "_still"), createLoc(Names.Fluids.XP_JUICE + "_flowing"))
                    .setDensity(1500));
            GameRegistry.register(blockXpJuice = new BlockFluidClassic(fluidXpJuice, new MaterialLiquid(MapColor.EMERALD))
                    .setRegistryName(fluidXpJuice.getName())
                    .setUnlocalizedName(fluidXpJuice.getName())
                    .setCreativeTab(BitsAndBobs.BAB_TAB));
            GameRegistry.register(new ItemBlock(blockXpJuice).setRegistryName(blockXpJuice.getRegistryName()));
        }
    }

    public static void postInit()
    {
        //Get the xp juice fluid if relying on another mod's fluid
        if(fluidXpJuice == null)
        {
            fluidXpJuice = FluidRegistry.getFluid(Names.Fluids.XP_JUICE);
            if(fluidXpJuice == null)
                LogHelper.info("Liquid XP Juice could not be found.");
        }
    }

    public static void regModels()
    {
        if(blockXpJuice != null)
            ClientUtils.regFluidModel((IFluidBlock) blockXpJuice);
    }
}

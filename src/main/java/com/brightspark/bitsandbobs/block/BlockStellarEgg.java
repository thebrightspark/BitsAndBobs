package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.BlockDragonEgg;

public class BlockStellarEgg extends BlockDragonEgg
{
    public BlockStellarEgg()
    {
        super();
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(Names.Blocks.STELLAR_EGG);
        setRegistryName(Names.Blocks.STELLAR_EGG);
        setLightLevel(1f);
    }
}

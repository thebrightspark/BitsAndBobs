package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import net.minecraft.block.BlockDragonEgg;

public class BlockStellarEgg extends BlockDragonEgg
{
    public BlockStellarEgg()
    {
        super();
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName("stellarEgg");
        setRegistryName("stellarEgg");
        setLightLevel(1f);
    }
}

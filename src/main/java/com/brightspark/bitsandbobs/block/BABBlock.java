package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BABBlock extends Block
{
    public BABBlock(String name)
    {
        this(name, Material.ROCK);
    }

    public BABBlock(String name, Material materialIn)
    {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setHardness(2f);
        setResistance(10f);
    }
}

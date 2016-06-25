package com.brightspark.bitsandbobs.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBasic extends ItemBlock
{
    public ItemBlockBasic(Block block)
    {
        super(block);
        setRegistryName(block.getRegistryName());
    }
}

package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.BitsAndBobs;
import net.minecraft.item.Item;

public class ItemBasic extends Item
{
    public ItemBasic(String itemName)
    {
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(itemName);
        setRegistryName(itemName);
    }
}

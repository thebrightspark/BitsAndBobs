package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ItemModelMesherForge;

public class Common
{
    private static ItemModelMesherForge m = (ItemModelMesherForge) Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
    //Register a model
    public static void regModel(Item item)
    {
        regModel(item, 0);
    }
    public static void regModel(Block block)
    {
        regModel(Item.getItemFromBlock(block), 0);
    }

    //Register a model with meta
    public static void regModel(Item item, int meta)
    {
        String itemName = item.getUnlocalizedName();
        LogHelper.info("Registering texture " + Reference.ITEM_TEXTURE_DIR + itemName.substring(itemName.indexOf(".") + 1));
        m.register(item, meta, new ModelResourceLocation(Reference.ITEM_TEXTURE_DIR + itemName.substring(itemName.indexOf(".") + 1), "inventory"));
    }
}

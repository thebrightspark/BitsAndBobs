package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.entity.TrailingFX;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.ItemModelMesherForge;

public class Common
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private static ItemModelMesherForge m = (ItemModelMesherForge) mc.getRenderItem().getItemModelMesher();
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

    public static void spawnEffect(EntityFX effect)
    {
        mc.effectRenderer.addEffect(effect);
    }

    public static void spawnTwirlEffect(World world, Entity entity)
    {
        spawnEffect(new TrailingFX(world, entity, 6, 82, 160).setChildRGBColour(0.6f, 1f, 0.6f));
    }
}

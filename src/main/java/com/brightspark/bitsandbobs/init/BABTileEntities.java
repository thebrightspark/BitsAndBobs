package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABTileEntities
{
    private static void regTE(Class<? extends TileEntity> teClass, Block block)
    {
        GameRegistry.registerTileEntity(teClass, block.getRegistryName().getResourcePath());
    }

    public static void init()
    {
        regTE(TileHealing.class, BABBlocks.blockHealing);
        regTE(TileTrash.class, BABBlocks.blockTrash);
    }
}

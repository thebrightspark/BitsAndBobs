package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileHealing.class, Names.Blocks.HEALING);
    }
}

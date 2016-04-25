package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABBlocks
{
    public static BlockHealing blockHealing = new BlockHealing();

    public static void init()
    {
        GameRegistry.registerBlock(blockHealing, Names.Blocks.HEALING);
    }

    public static void regModels()
    {
        Common.regModel(blockHealing);
    }
}

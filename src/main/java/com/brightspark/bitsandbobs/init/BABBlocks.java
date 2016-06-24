package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABBlocks
{
    public static BlockHealing blockHealing = new BlockHealing(Names.Blocks.HEALING, 1000, 20);
    public static BlockHealing blockHealing2 = new BlockHealing(Names.Blocks.HEALING_2, 2000, 5);

    public static void init()
    {
        GameRegistry.registerBlock(blockHealing, Names.Blocks.HEALING);
        GameRegistry.registerBlock(blockHealing2, Names.Blocks.HEALING_2);
    }

    public static void regModels()
    {
        Common.regModel(blockHealing);
        Common.regModel(blockHealing2);
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.block.BlockStellarEgg;
import com.brightspark.bitsandbobs.item.ItemBlockBasic;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABBlocks
{
    public static BlockHealing blockHealing = new BlockHealing(Names.Blocks.HEALING, 1000, 20);
    public static BlockHealing blockHealing2 = new BlockHealing(Names.Blocks.HEALING_2, 2000, 5);
    public static BlockStellarEgg blockStellarEgg = new BlockStellarEgg();

    private static void regBlock(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlockBasic(block));
    }

    public static void init()
    {
        regBlock(blockHealing);
        regBlock(blockHealing2);
        regBlock(blockStellarEgg);
    }

    public static void regModels()
    {
        Common.regModel(blockHealing);
        Common.regModel(blockHealing2);
        Common.regModel(blockStellarEgg);
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.block.BlockStellarEgg;
import com.brightspark.bitsandbobs.block.BlockTrash;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABBlocks
{
    //Contains all of the registered blocks
    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static BlockHealing blockHealing, blockHealing2;
    public static BlockStellarEgg blockStellarEgg;
    public static BlockTrash blockTrash;

    public static void regBlock(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register((new ItemBlock(block)).setRegistryName(block.getRegistryName()));
        BLOCKS.add(block);
    }

    public static void init()
    {
        regBlock(blockHealing = new BlockHealing(Names.Blocks.HEALING, 1000, 20));
        regBlock(blockHealing2 = new BlockHealing(Names.Blocks.HEALING_2, 2000, 5));
        regBlock(blockStellarEgg = new BlockStellarEgg());
        regBlock(blockTrash = new BlockTrash());
    }

    public static void regModels()
    {
        for(Block block : BLOCKS)
            Common.regModel(block);
    }
}

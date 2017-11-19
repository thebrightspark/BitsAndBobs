package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.block.*;
import com.brightspark.bitsandbobs.tileentity.TileChatter;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorch;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import com.brightspark.bitsandbobs.util.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABBlocks
{
    //Contains all of the registered blocks
    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static Block blockHealing, blockHealing2, blockStellarEgg, blockTrash, blockLightning, blockChatter,
            blockTorchItem, blockTorchLiving, blockTorchPlayer, blockTorchOther, blockTorchAll;

    public static void regBlock(Block block)
    {
        GameRegistry.register(block);
        GameRegistry.register((new ItemBlock(block)).setRegistryName(block.getRegistryName()));
        BLOCKS.add(block);
    }

    private static void regTE(Class<? extends TileEntity> teClass, Block block)
    {
        GameRegistry.registerTileEntity(teClass, block.getRegistryName().getResourcePath());
    }

    public static void regBlocks()
    {
        regBlock(blockHealing = new BlockHealing("healing", 1000, 20));
        regBlock(blockHealing2 = new BlockHealing("healing2", 2000, 5));
        regBlock(blockStellarEgg = new BlockStellarEgg());
        regBlock(blockTrash = new BlockTrash());
        regBlock(blockLightning = new BlockLightning());
        //regBlock(blockChatter = new BlockChatter());
        regBlock(blockTorchItem = new BlockInterdictionTorchItem());
        regBlock(blockTorchLiving = new BlockInterdictionTorchLiving());
        regBlock(blockTorchPlayer = new BlockInterdictionTorchPlayer());
    }

    public static void regModels()
    {
        for(Block block : BLOCKS)
            ClientUtils.regModel(block);
    }

    public static void regTEs()
    {
        regTE(TileHealing.class, blockHealing);
        regTE(TileTrash.class, blockTrash);
        //regTE(TileChatter.class, blockChatter);
        regTE(TileInterdictionTorch.class, blockTorchItem);
    }
}

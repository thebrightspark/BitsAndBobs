package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHealing extends BlockContainer
{
    public BlockHealing()
    {
        super(Material.rock);
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(Names.Blocks.HEALING);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileHealing();
    }

    //Return 3 for standard block models
    public int getRenderType()
    {
        return 3;
    }
}

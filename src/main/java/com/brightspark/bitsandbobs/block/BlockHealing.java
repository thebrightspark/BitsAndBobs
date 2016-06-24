package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHealing extends BlockContainer
{
    private final int ticks;
    private final int fuelMax;

    public BlockHealing(String name, int maxFuelStorage, int ticksBetweenChecks)
    {
        super(Material.rock);
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(name);
        fuelMax = maxFuelStorage;
        ticks = ticksBetweenChecks;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileHealing(fuelMax, ticks);
    }

    //Return 3 for standard block models
    public int getRenderType()
    {
        return 3;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote && !player.isSneaking())
            player.openGui(BitsAndBobs.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}

package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockHealing extends BlockContainer
{
    private final int ticks;
    private final int fuelMax;

    public BlockHealing(String name, int maxFuelStorage, int ticksBetweenChecks)
    {
        super(Material.ROCK);
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(2f);
        setResistance(10f);
        fuelMax = maxFuelStorage;
        ticks = ticksBetweenChecks;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        LogHelper.info("Creating TileHealing");
        TileHealing te = new TileHealing();
        te.setFuelMax(fuelMax);
        te.setTicksBetweenChecks(ticks);
        return te;
    }

    //Return 3 for standard block models
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote && !player.isSneaking())
            player.openGui(BitsAndBobs.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}

package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.BitsAndBobs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BABBlockContainer extends BlockContainer
{
    public BABBlockContainer(Material material, String name)
    {
        super(material);
        setCreativeTab(BitsAndBobs.BAB_TAB);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(2f);
        setResistance(10f);
    }

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

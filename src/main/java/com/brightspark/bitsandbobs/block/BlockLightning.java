package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLightning extends BABBlock
{
    public BlockLightning()
    {
        super(Names.Blocks.LIGHTNING);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking()) return false;

        //Spawn lightning
        if(!world.isRemote)
            world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.up().getY(), pos.getZ(), true));

        return true;
    }
}

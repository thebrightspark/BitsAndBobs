package com.brightspark.bitsandbobs.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLightning extends BABBlock
{
    public BlockLightning()
    {
        super("lightning");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking()) return false;

        //Spawn lightning
        if(!world.isRemote)
            world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.up().getY(), pos.getZ(), true));

        return true;
    }
}

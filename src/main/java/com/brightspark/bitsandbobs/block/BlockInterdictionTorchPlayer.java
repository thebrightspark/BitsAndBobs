package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInterdictionTorchPlayer extends AbstractInterdictionTorch
{
    public BlockInterdictionTorchPlayer()
    {
        super("interdictionTorchPlayer");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileInterdictionTorch(EntityPlayer.class);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null && te instanceof TileInterdictionTorch)
                ((TileInterdictionTorch) te).setPlacer((EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }
}

package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorchPlayer;
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
        super("interdiction_torch_player");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileInterdictionTorchPlayer();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            TileEntity te = world.getTileEntity(pos);
            if(te != null && te instanceof TileInterdictionTorchPlayer)
                ((TileInterdictionTorchPlayer) te).setPlacer((EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }
}

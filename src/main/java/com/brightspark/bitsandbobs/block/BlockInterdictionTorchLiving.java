package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorch;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInterdictionTorchLiving extends AbstractInterdictionTorch
{
    public BlockInterdictionTorchLiving()
    {
        super("interdictionTorchLiving");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileInterdictionTorch(EntityLiving.class, EntityPlayer.class);
    }
}

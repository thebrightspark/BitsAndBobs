package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorchLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInterdictionTorchLiving extends AbstractInterdictionTorch
{
    public BlockInterdictionTorchLiving()
    {
        super("interdiction_torch_living");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileInterdictionTorchLiving();
    }
}

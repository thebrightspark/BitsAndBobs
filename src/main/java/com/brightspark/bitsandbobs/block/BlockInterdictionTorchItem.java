package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileInterdictionTorchItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInterdictionTorchItem extends AbstractInterdictionTorch
{
    public BlockInterdictionTorchItem()
    {
        super("interdiction_torch_item");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileInterdictionTorchItem();
    }
}

package com.brightspark.bitsandbobs.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class TileInterdictionTorchLiving extends TileInterdictionTorch
{
    public TileInterdictionTorchLiving()
    {
        super(EntityLiving.class, EntityPlayer.class);
    }
}

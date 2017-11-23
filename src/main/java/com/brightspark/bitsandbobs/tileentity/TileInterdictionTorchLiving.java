package com.brightspark.bitsandbobs.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class TileInterdictionTorchLiving extends TileInterdictionTorch
{
    public TileInterdictionTorchLiving()
    {
        super(new Color(1F, 0F, 0F), EntityLiving.class, EntityPlayer.class);
    }
}

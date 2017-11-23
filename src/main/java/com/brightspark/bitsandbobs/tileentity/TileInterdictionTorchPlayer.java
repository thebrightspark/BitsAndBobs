package com.brightspark.bitsandbobs.tileentity;

import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class TileInterdictionTorchPlayer extends TileInterdictionTorch
{
    public TileInterdictionTorchPlayer()
    {
        super(new Color(0F, 0F, 1F), EntityPlayer.class);
    }
}

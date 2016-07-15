package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHealing extends BABBlockContainer
{
    private final int ticks;
    private final int fuelMax;

    public BlockHealing(String name, int maxFuelStorage, int ticksBetweenChecks)
    {
        super(Material.ROCK, name);
        fuelMax = maxFuelStorage;
        ticks = ticksBetweenChecks;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        LogHelper.info("Creating TileHealing");
        TileHealing te = new TileHealing();
        te.setFuelMax(fuelMax);
        te.setTicksBetweenChecks(ticks);
        te.setDefaultName(getRegistryName().getResourcePath());
        return te;
    }
}

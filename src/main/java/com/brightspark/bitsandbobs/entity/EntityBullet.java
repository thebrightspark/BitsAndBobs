package com.brightspark.bitsandbobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBullet extends Entity implements IProjectile
{
    //TODO: Make bullet entity - don't forget a renderer!

    private float damage = 4f;

    public EntityBullet(World world)
    {
        super(world);
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {

    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {

    }
}

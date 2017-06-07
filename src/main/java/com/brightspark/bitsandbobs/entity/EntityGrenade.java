package com.brightspark.bitsandbobs.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable
{
    private static final int delay = 60; //3 secs
    private long delayEnd = -1;

    public EntityGrenade(World world)
    {
        super(world);
        setDelay(world.getTotalWorldTime());
    }

    public EntityGrenade(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
        setHeadingFromThrower(thrower, thrower.rotationPitch, thrower.rotationYaw, 0f, 1f, 1f);
        setDelay(world.getTotalWorldTime());
    }

    private void setDelay(long worldTotalTime)
    {
        delayEnd = worldTotalTime + delay;
    }

    private void explode()
    {
        if(!world.isRemote) world.createExplosion(this, posX, posY, posZ, 2f, true);
        setDead();
    }

    public void onUpdate()
    {
        if(world.getTotalWorldTime() >= delayEnd) explode();
        super.onUpdate();
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(result.typeOfHit == RayTraceResult.Type.ENTITY)
            //Explode on hitting an entity
            explode();
        else if(result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            //Bounce off of blocks
            switch(result.sideHit)
            {
                case NORTH:
                case SOUTH:
                    motionZ *= -0.6d;
                    break;
                case EAST:
                case WEST:
                    motionX *= -0.6d;
                    break;
                case DOWN:
                case UP:
                    motionX *= 0.6d;
                    motionY *= -0.5d;
                    motionZ *= 0.6d;
            }

            /*
            if(onGround)
            {
                if(Math.abs(motionX) < 0.1d) motionX = 0d;
                if(Math.abs(motionY) < 0.1d) motionY = 0d;
                if(Math.abs(motionZ) < 0.1d) motionZ = 0d;
            }
            */
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setLong("delayEnd", delayEnd);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        delayEnd = nbt.getLong("delayEnd");
    }
}

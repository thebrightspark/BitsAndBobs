package com.brightspark.bitsandbobs.entity;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class StaticFX extends EntityFX
{
    private Entity attachedEntity;
    private double relX, relY, relZ;

    public StaticFX(World world, Entity entity, double relativeX, double relativeY, double relativeZ, int maxAge, int textureIndex)
    {
        this(world, entity.posX + relativeX, entity.posY + relativeY, entity.posZ + relativeZ, maxAge, textureIndex);
        attachedEntity = entity;
        relX = relativeX;
        relY = relativeY;
        relZ = relativeZ;
    }

    public StaticFX(World world, double x, double y, double z, int maxAge, int textureIndex)
    {
        super(world, x, y, z);
        motionX = motionY = motionZ = 0;
        particleMaxAge = maxAge;
        setParticleTextureIndex(textureIndex);
        noClip = true;
    }

    /**
     * Set a new position for this particle relative to the attached entity.
     * @param entity Entity to attach to
     * @param relativeX X position relative to attached entity
     * @param relativeY Y position relative to attached entity
     * @param relativeZ Z position relative to attached entity
     * @return StaticFX entity
     */
    public StaticFX setTrackedPos(Entity entity, double relativeX, double relativeY, double relativeZ)
    {
        attachedEntity = entity;
        relX = relativeX;
        relY = relativeY;
        relZ = relativeZ;
        return this;
    }

    /**
     * Does same as setRGBColorF(), but returns this object as well.
     * @param r Red colour
     * @param g Green colour
     * @param b Blue colour
     * @return StaticFX entity
     */
    public StaticFX setRBG(float r, float g, float b)
    {
        super.setRBGColorF(r, g, b);
        return this;
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        if(attachedEntity != null)
        {
            //Move entity
            moveEntity(motionX, motionY, motionZ);

            //Calculate motion to get to new position
            motionX = attachedEntity.posX + relX - posX;
            motionY = attachedEntity.posY + relY - posY;
            motionZ = attachedEntity.posZ + relZ - posZ;
        }
    }
}

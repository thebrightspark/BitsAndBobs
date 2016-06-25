package com.brightspark.bitsandbobs.entity;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ParticleStatic extends Particle
{
    private Entity attachedEntity;
    private double relX, relY, relZ;
    private boolean isNoClip;

    public ParticleStatic(World world, Entity entity, double relativeX, double relativeY, double relativeZ, int maxAge, int textureIndex)
    {
        this(world, entity.posX + relativeX, entity.posY + relativeY, entity.posZ + relativeZ, maxAge, textureIndex);
        attachedEntity = entity;
        relX = relativeX;
        relY = relativeY;
        relZ = relativeZ;
    }

    public ParticleStatic(World world, double x, double y, double z, int maxAge, int textureIndex)
    {
        super(world, x, y, z);
        motionX = motionY = motionZ = 0;
        particleMaxAge = maxAge;
        isNoClip = true;
        setParticleTextureIndex(textureIndex);
    }

    /**
     * Set a new position for this particle relative to the attached entity.
     * @param entity Entity to attach to
     * @param relativeX X position relative to attached entity
     * @param relativeY Y position relative to attached entity
     * @param relativeZ Z position relative to attached entity
     * @return StaticFX entity
     */
    public ParticleStatic setTrackedPos(Entity entity, double relativeX, double relativeY, double relativeZ)
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
    public ParticleStatic setRBG(float r, float g, float b)
    {
        super.setRBGColorF(r, g, b);
        return this;
    }

    /**
     * Sets whether this particle should move through blocks.
     * @param noClip
     * @return This particle.
     */
    public ParticleStatic setNoClip(boolean noClip)
    {
        isNoClip = noClip;
        return this;
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
            this.setExpired();

        if(attachedEntity != null)
            setPosition(attachedEntity.posX + relX,
                        attachedEntity.posY + relY,
                        attachedEntity.posZ + relZ);
    }
}

package com.brightspark.bitsandbobs.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ParticleTrailing extends Particle
{
    private Entity attachedEntity;
    private double radius;
    private boolean isNoClip;
    private ParticleManager er = Minecraft.getMinecraft().effectRenderer;

    //Child settings
    private int childMaxAge;
    private int childTextureIndex;
    private float childRed = 1f;
    private float childGreen = 1f;
    private float childBlue = 1f;

    private float angle = 0; //Angle position around center of entity
    private float angleSpeed = 0.5f;
    private float height = 0; //Height relative to entity
    private float heightSpeed = 0.05f;

    public ParticleTrailing(World world, Entity entity, int trailLength, int textureIndex, int childParticleTextureIndex)
    {
        this(world, entity, textureIndex);
        childMaxAge = trailLength;
        childTextureIndex = childParticleTextureIndex;
    }

    private ParticleTrailing(World world, Entity entity, int textureIndex)
    {
        super(world, entity.posX, entity.posY, entity.posZ);
        motionX = motionY = motionZ = 0;
        attachedEntity = entity;
        AxisAlignedBB entityBB = entity.getEntityBoundingBox();
        double xSize = entityBB.maxX - entityBB.minX;
        double ySize = entityBB.maxY - entityBB.minY;
        double zSize = entityBB.maxZ - entityBB.minZ;
        radius = Math.max(xSize, zSize);
        particleMaxAge = (int) Math.ceil(ySize/heightSpeed);
        isNoClip = true;
        setParticleTextureIndex(textureIndex);
    }

    public ParticleTrailing setChildRGBColour(float r, float g, float b)
    {
        childRed = r;
        childGreen = g;
        childBlue = b;
        return this;
    }

    public ParticleTrailing setAngle(int angle)
    {
        this.angle = angle;
        return this;
    }

    /**
     * Sets whether this particle should move through blocks.
     * @param noClip
     * @return This particle.
     */
    public ParticleTrailing setNoClip(boolean noClip)
    {
        isNoClip = noClip;
        return this;
    }

    /*
    public void moveEntity(double x, double y, double z)
    {
        if(isNoClip)
        {
            setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
            resetPositionToBB();
        }
        else
            super.moveEntity(x, y, z);
    }
    */

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
            this.setExpired();

        //Spawn child particle before moving
        er.addEffect(new ParticleDisappearingStatic(world, attachedEntity, posX - attachedEntity.posX, posY - attachedEntity.posY, posZ - attachedEntity.posZ, childMaxAge, childTextureIndex).setRBG(childRed, childGreen, childBlue));

        //Increase height and angle
        angle += angleSpeed;
        height += heightSpeed;

        //Set position
        setPosition(attachedEntity.posX + radius * Math.cos(angle),
                    attachedEntity.posY + height,
                    attachedEntity.posZ + radius * Math.sin(angle));
    }
}

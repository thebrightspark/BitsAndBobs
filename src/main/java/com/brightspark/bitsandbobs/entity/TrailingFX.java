package com.brightspark.bitsandbobs.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TrailingFX extends EntityFX
{
    private Entity attachedEntity;
    private double radius;
    private EffectRenderer er = Minecraft.getMinecraft().effectRenderer;

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

    public TrailingFX(World world, Entity entity, int trailLength, int textureIndex, int childParticleTextureIndex)
    {
        this(world, entity, textureIndex);
        childMaxAge = trailLength;
        childTextureIndex = childParticleTextureIndex;
    }

    private TrailingFX(World world, Entity entity, int textureIndex)
    {
        super(world, entity.posX, entity.posY, entity.posZ);
        motionX = motionY = motionZ = 0;
        attachedEntity = entity;
        AxisAlignedBB entityBB = entity.getEntityBoundingBox();
        double xSize = entityBB.maxX - entityBB.minX;
        double ySize = entityBB.maxY - entityBB.minY;
        double zSize = entityBB.maxZ - entityBB.minZ;
        radius = (Math.max(xSize, zSize) / 2) * 1.5;
        particleMaxAge = (int) Math.ceil(ySize/heightSpeed);
        noClip = true;
        setParticleTextureIndex(textureIndex);
    }

    public TrailingFX setChildRGBColour(float r, float g, float b)
    {
        childRed = r;
        childGreen = g;
        childBlue = b;
        return this;
    }

    public TrailingFX setAngle(int angle)
    {
        this.angle = angle;
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

        //Spawn child particle before moving
        er.addEffect(new DisappearingStaticFX(worldObj, attachedEntity, posX - attachedEntity.posX, posY - attachedEntity.posY, posZ - attachedEntity.posZ, childMaxAge, childTextureIndex).setRBG(childRed, childGreen, childBlue));

        //Move entity
        moveEntity(motionX, motionY, motionZ);

        //Increase height and angle
        angle += angleSpeed;
        height += heightSpeed;

        //Calculate motion to get to new position
        motionX = (attachedEntity.posX + radius * Math.cos(angle)) - posX;
        motionY = (attachedEntity.posY + height) - posY;
        motionZ = (attachedEntity.posZ + radius * Math.sin(angle)) - posZ;
    }
}

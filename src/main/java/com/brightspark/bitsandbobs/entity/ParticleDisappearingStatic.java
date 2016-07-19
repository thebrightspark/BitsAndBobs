package com.brightspark.bitsandbobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleDisappearingStatic extends ParticleStatic
{
    public ParticleDisappearingStatic(World world, Entity entity, double relativeX, double relativeY, double relativeZ, int maxAge, int textureIndex)
    {
        super(world, entity, relativeX, relativeY, relativeZ, maxAge, textureIndex);
    }

    public ParticleDisappearingStatic(World world, double xPos, double yPos, double zPos, int maxAge, int textureIndex)
    {
        super(world, xPos, yPos, zPos, maxAge, textureIndex);
    }

    @Override
    public void onUpdate()
    {
        float age = (float) particleAge;
        float max = (float) particleMaxAge;
        setAlphaF((max-age) / max);

        super.onUpdate();
    }
}

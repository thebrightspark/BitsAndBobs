package com.brightspark.bitsandbobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class DisappearingStaticFX extends StaticFX
{
    public DisappearingStaticFX(World world, Entity entity, double relativeX, double relativeY, double relativeZ, int maxAge, int textureIndex)
    {
        super(world, entity, relativeX, relativeY, relativeZ, maxAge, textureIndex);
    }

    public void onUpdate()
    {
        float age = (float) particleAge;
        float max = (float) particleMaxAge;
        setAlphaF((max-age) / max);

        super.onUpdate();
    }
}

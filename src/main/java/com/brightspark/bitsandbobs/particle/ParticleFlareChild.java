package com.brightspark.bitsandbobs.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleFlareChild extends Particle
{
    public ParticleFlareChild(World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ)
    {
        super(world, posX, posY, posZ);
        motionX = changeMotionComponentFromSpeed(speedX);
        motionY = changeMotionComponentFromSpeed(speedY);
        motionZ = changeMotionComponentFromSpeed(speedZ);
        particleMaxAge = 40; //2 sec
        setRBGColorF(1f, 0.15f + (rand.nextFloat() / 5f) - 0.1f, 0.15f + (rand.nextFloat() / 5f) - 0.1f);
        multipleParticleScaleBy(2);
    }

    private double changeMotionComponentFromSpeed(double speed)
    {
        double motion = speed * (rand.nextDouble() / 4d + 0.75d);
        if(Math.abs(motion) < 0.125d)
            motion = rand.nextDouble() / 4d - 0.125d;
        else if(Math.abs(motion) < 0.25d)
            motion = rand.nextDouble() / 4d * (speed < 0 ? -1 : 1);
        return motion;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
            setExpired();

        if(particleAge < 8)
            setParticleTextureIndex(particleAge);
        else
            setParticleTextureIndex((particleMaxAge - particleAge) / 4);

        motionY -= 0.04d;
        move(motionX, motionY, motionZ);
        motionX *= 0.8d;
        motionY *= 0.8d;
        motionZ *= 0.8d;

        if (isCollided)
        {
            motionX *= 0.6d;
            motionZ *= 0.6d;
        }
    }
}

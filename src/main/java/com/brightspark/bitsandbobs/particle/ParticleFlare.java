package com.brightspark.bitsandbobs.particle;

import com.brightspark.bitsandbobs.util.ClientUtils;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ParticleFlare extends Particle
{
    private boolean isCollided = false;

    public ParticleFlare(World world, double posX, double posY, double posZ, Vec3d lookVec)
    {
        super(world, posX, posY, posZ);
        float velocity = 3f + ((rand.nextFloat() / 5f) - 0.025f);
        motionX = lookVec.x * velocity;
        motionY = lookVec.y * velocity;
        motionZ = lookVec.z * velocity;
        particleMaxAge = 100; //5 secs
        setParticleTextureIndex(144);
        setRBGColorF(1f, 0f, 0f);
        multipleParticleScaleBy(6);
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
            setExpired();

        //Changes to the smaller particles as the age gets closer to the max age
        if(particleAge < 8)
            setParticleTextureIndex(144 + particleAge);
        else if(particleAge > particleMaxAge - 32)
            setParticleTextureIndex(144 + (particleMaxAge - particleAge) / 4);

        //Spawn child particles
        for(int i = rand.nextInt(4) + 1; i > 0; i--)
            ClientUtils.spawnEffect(new Child(world, posX, posY, posZ, motionX, motionY, motionZ));

        motionY -= 0.01d;
        move(motionX, motionY, motionZ);
        motionX *= 0.98d;
        motionY *= 0.95d;
        motionZ *= 0.98d;

        if (isCollided)
        {
            motionX *= 0.75d;
            motionZ *= 0.75d;
        }
    }

    @Override
    public void move(double x, double y, double z)
    {
        double d0 = x;
        double d1 = y;
        double d2 = z;
        List<AxisAlignedBB> list = world.getCollisionBoxes(null, getBoundingBox().offset(x, y, z));

        for(AxisAlignedBB axisalignedbb : list)
            y = axisalignedbb.calculateYOffset(getBoundingBox(), y);

        setBoundingBox(getBoundingBox().offset(0.0D, y, 0.0D));

        for(AxisAlignedBB axisalignedbb1 : list)
            x = axisalignedbb1.calculateXOffset(getBoundingBox(), x);

        setBoundingBox(getBoundingBox().offset(x, 0.0D, 0.0D));

        for(AxisAlignedBB axisalignedbb2 : list)
            z = axisalignedbb2.calculateZOffset(getBoundingBox(), z);

        setBoundingBox(getBoundingBox().offset(0.0D, 0.0D, z));
        resetPositionToBB();
        isCollided = d1 != y && d1 < 0.0D;

        //These have been changed to bounce the particle instead of just stopping at a block
        if(d0 != x)
            motionX *= -1;
        if(d1 != y)
            motionY *= -1;
        if(d2 != z)
            motionZ *= -1;
    }

    private class Child extends Particle
    {
        public Child(World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ)
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
}

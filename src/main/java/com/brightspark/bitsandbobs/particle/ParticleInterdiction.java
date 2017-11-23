package com.brightspark.bitsandbobs.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class ParticleInterdiction extends Particle
{
    private AxisAlignedBB torchArea;

    public ParticleInterdiction(World worldIn, BlockPos pos, Color colour, AxisAlignedBB area)
    {
        super(worldIn, ((double) pos.getX()) + 0.5D, ((double) pos.getY()) + 0.75D, ((double) pos.getZ()) + 0.5D);
        torchArea = area;
        particleMaxAge = 200;
        multipleParticleScaleBy(0.75F);
        //Give the colour of this particle a random darkness
        float darkScale = rand.nextFloat();
        setRBGColorF(
                (int) (colour.getRed() * darkScale),
                (int) (colour.getGreen() * darkScale),
                (int) (colour.getBlue() * darkScale));
        //Set a random motion
        double speed = (rand.nextDouble() / 6D) + 0.1D;
        double[] dirs = new double[] {rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D, rand.nextDouble() - 0.5D};
        double dirTotal = Math.abs(dirs[0]) + Math.abs(dirs[1]) + Math.abs(dirs[2]);
        for(int i = 0; i < dirs.length; i++)
            dirs[i] /= dirTotal;
        motionX = dirs[0] * speed;
        motionY = dirs[1] * speed;
        motionZ = dirs[2] * speed;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
            setExpired();

        move(motionX, motionY, motionZ);

        //Kill particle if it reaches edge of the torch's range
        if(!torchArea.contains(new Vec3d(posX, posY, posZ)))
            setExpired();
    }

    @Override
    public void move(double x, double y, double z)
    {
        double origX = x;
        double origY = y;
        double origZ = z;
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

        //If collided with a block, then die
        if(origY != y && origY < 0.0D)
            setExpired();

        if(origX != x)
            motionX = 0;
        if(origY != y)
            motionY = 0;
        if(origZ != z)
            motionZ = 0;
    }
}

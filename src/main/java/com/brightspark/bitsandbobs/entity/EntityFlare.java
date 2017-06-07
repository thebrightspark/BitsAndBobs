package com.brightspark.bitsandbobs.entity;

import com.brightspark.bitsandbobs.particle.ParticleFlareChild;
import com.brightspark.bitsandbobs.util.ClientUtils;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityFlare extends Entity
{
    protected int age;

    public EntityFlare(World world)
    {
        super(world);
        age = 100; //5 secs
    }

    public EntityFlare(World world, double posX, double posY, double posZ, Vec3d lookVec)
    {
        this(world);
        setPosition(posX, posY, posZ);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float velocity = 3f + ((rand.nextFloat() / 5f) - 0.025f);
        motionX = lookVec.xCoord * velocity;
        motionY = lookVec.yCoord * velocity;
        motionZ = lookVec.zCoord * velocity;
    }

    public EntityFlare(World world, Entity entity)
    {
        this(world, entity.posX - 0.5d, entity.posY + 1d, entity.posZ - 0.5d, entity.getLookVec());
    }

    public void onEntityUpdate()
    {
        world.theProfiler.startSection("entityBaseTick");

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;

        if(age-- <= 0)
            setDead();

        //Spawn child particles
        /*
        if(world.isRemote)
            for(int i = rand.nextInt(4) + 1; i > 0; i--)
                ClientUtils.spawnEffect(new ParticleFlareChild(world, posX, posY, posZ, motionX, motionY, motionZ));
        */
        //Move entity
        motionY -= 0.01d;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98d;
        motionY *= 0.95d;
        motionZ *= 0.98d;

        //LogHelper.info("Moved to: " + posX + ", " + posY + ", " + posZ);

        if (isCollided)
        {
            motionX *= 0.75d;
            motionZ *= 0.75d;
        }

        if(!world.isRemote && world instanceof WorldServer)
        {
            world.theProfiler.startSection("portal");

            if(inPortal)
            {
                MinecraftServer minecraftserver = world.getMinecraftServer();

                if(minecraftserver.getAllowNether())
                {
                    int i = getMaxInPortalTime();

                    if(portalCounter++ >= i)
                    {
                        portalCounter = i;
                        timeUntilPortal = getPortalCooldown();
                        int j = world.provider.getDimensionType().getId() == -1 ? 0 : -1;
                        changeDimension(j);
                    }

                    inPortal = false;
                }
            }
            else
            {
                if(portalCounter > 0)
                    portalCounter -= 4;
                if(portalCounter < 0)
                    portalCounter = 0;
            }

            decrementTimeUntilPortal();
            world.theProfiler.endSection();
        }

        handleWaterMovement();

        if(posY < -64.0D)
            kill();

        firstUpdate = false;
        world.theProfiler.endSection();
    }

    public void moveEntity(double x, double y, double z)
    {
        world.theProfiler.startSection("move");

        double d0 = x;
        double d1 = y;
        double d2 = z;

        if(isInWeb)
        {
            isInWeb = false;
            x *= 0.25D;
            y *= 0.05000000074505806D;
            z *= 0.25D;
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
        }

        List<AxisAlignedBB> list = world.getCollisionBoxes(null, getEntityBoundingBox().addCoord(x, y, z));

        for(AxisAlignedBB axisalignedbb : list)
            y = axisalignedbb.calculateYOffset(getEntityBoundingBox(), y);

        setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));

        for(AxisAlignedBB axisalignedbb1 : list)
            x = axisalignedbb1.calculateXOffset(getEntityBoundingBox(), x);

        setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));

        for(AxisAlignedBB axisalignedbb2 : list)
            z = axisalignedbb2.calculateZOffset(getEntityBoundingBox(), z);

        setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
        isCollided = d1 != y && d1 < 0.0D;

        //These have been changed to bounce the particle instead of just stopping at a block
        if(d0 != x)
            motionX *= -1;
        if(d1 != y)
            motionY *= -1;
        if(d2 != z)
            motionZ *= -1;

        world.theProfiler.endSection();
        world.theProfiler.startSection("rest");
        resetPositionToBB();

        try
        {
            doBlockCollisions();
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }

        world.theProfiler.endSection();
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {

    }
}

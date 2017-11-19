package com.brightspark.bitsandbobs.tileentity;

import com.brightspark.bitsandbobs.reference.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.function.Predicate;

public class TileInterdictionTorch extends BABTileEntity implements ITickable
{
    private EntityPlayer placer;
    private Class<? extends Entity> entityToAffect;
    private Predicate<Entity> entityFilter;
    private AxisAlignedBB area;
    private Vec3d centerPos;

    public TileInterdictionTorch(Class<? extends Entity> entityToAffect)
    {
        this(entityToAffect, null);
    }

    public TileInterdictionTorch(Class<? extends Entity> entityToAffect, Class<? extends Entity> entityToExclude)
    {
        this.entityToAffect = entityToAffect;
        entityFilter = ((Predicate<Entity>) EntitySelectors.NOT_SPECTATING::apply)
                .and(EntitySelectors.IS_ALIVE::apply)
                .and((entity) -> entityToExclude == null || !entityToExclude.isInstance(entity))
                .and((entity) -> !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        area = new AxisAlignedBB(pos).grow(Config.interdictionTorchRadius);
        centerPos = new Vec3d(pos).addVector(0.5D, 0.5D, 0.5D);
    }

    public void setPlacer(EntityPlayer player)
    {
        placer = player;
    }

    private Vec3d getNormalDirVec(Entity entity)
    {
        return entity.getPositionVector().subtract(centerPos).normalize().scale(Config.interdictionTorchStrength);
    }

    @Override
    public void update()
    {
        if(!world.isRemote)
        {
            //Every tick, push the entities away from this position
            List<Entity> entities = world.getEntitiesWithinAABB(entityToAffect, area, entityFilter::test);
            for(Entity entity : entities)
            {
                if(entity.equals(placer)) continue;
                Vec3d motion = getNormalDirVec(entity);
                entity.motionX += motion.x;
                entity.motionY += motion.y;
                entity.motionZ += motion.z;
            }
        }
        else
        {
            if(world.rand.nextFloat() > 0.4F)
            {
                //TODO: Particles!
            }
        }
    }
}

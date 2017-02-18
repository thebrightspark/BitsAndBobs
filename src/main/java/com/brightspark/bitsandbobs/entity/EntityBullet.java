package com.brightspark.bitsandbobs.entity;

import com.brightspark.bitsandbobs.util.CommonUtils;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityBullet extends Entity implements IProjectile
{
    //Copied from EntityArrow
    private static final Predicate<Entity> BULLET_TARGETS = Predicates.and(new Predicate[] {EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity.canBeCollidedWith();
        }
    }});

    private float damage = 4f;
    private int knockbackStrength = 1;
    private Entity shooter;
    private int ticksInAir = 0;
    private boolean shouldResetHurtTimer = false;

    public EntityBullet(World world)
    {
        super(world);
        setSize(0.2f, 0.2f);
    }

    public EntityBullet(World world, double x, double y, double z)
    {
        this(world);
        setPosition(x, y, z);
    }

    public EntityBullet(World world, EntityLivingBase shooter)
    {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1d, shooter.posZ);
        this.shooter = shooter;
        setHeadingFromShooter(shooter, 5f);
    }

    public EntityBullet setShouldResetHurtTimer()
    {
        shouldResetHurtTimer = true;
        return this;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        damage = nbt.getFloat("damage");
        knockbackStrength = nbt.getInteger("knockback");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setFloat("damage", damage);
        nbt.setInteger("knockback", knockbackStrength);
    }

    public void setHeadingFromShooter(Entity shooter, float velocity)
    {
        float rotYaw = shooter.rotationYaw;
        float rotPitch = shooter.rotationPitch;
        float x = -MathHelper.sin(rotYaw * 0.017453292f) * MathHelper.cos(rotPitch * 0.017453292f);
        float y = -MathHelper.sin(rotPitch * 0.017453292f);
        float z = MathHelper.cos(rotYaw * 0.017453292f) * MathHelper.cos(rotPitch * 0.017453292f);
        setThrowableHeading(x, y, z, velocity, 0);
        motionX += shooter.motionX;
        motionZ += shooter.motionZ;
        if(!shooter.onGround)
            motionY += shooter.motionY;
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        double magnitude = MathHelper.sqrt_double(x * x + y * y + z * z);
        x = (x / magnitude) * velocity;
        y = (y / magnitude) * velocity;
        z = (z / magnitude) * velocity;
        motionX = x;
        motionY = y;
        motionZ = z;
        double hMagnitude = MathHelper.sqrt_double(x * x + z * z);
        rotationYaw = (float) (MathHelper.atan2(x, z) * (180d / Math.PI));
        rotationPitch = (float)(MathHelper.atan2(y, hMagnitude) * (180D / Math.PI));
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
    }

    /**
     * Called when the bullet hits a block or an entity
     */
    protected void onHit(RayTraceResult ray)
    {
        Entity entityHit = ray.entityHit;
        if(entityHit != null)
        {
            //Hit an entity
            float magnitude = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);

            //Create damage source
            DamageSource damageSource;
            if(shooter == null)
                damageSource = CommonUtils.causeBulletDamage(this, this);
            else
                damageSource = CommonUtils.causeBulletDamage(this, shooter);

            //Hit entity
            if(entityHit.attackEntityFrom(damageSource, damage))
            {
                if(entityHit instanceof EntityLivingBase)
                {
                    EntityLivingBase entityLiving = (EntityLivingBase) entityHit;

                    //Knockback
                    if(magnitude > 0)
                        entityLiving.addVelocity(knockbackStrength * 0.6d / magnitude, 0.1d, knockbackStrength * 0.6d / magnitude);

                    //Apply enchantments
                    if(shooter instanceof EntityLivingBase)
                    {
                        EnchantmentHelper.applyThornEnchantments(entityLiving, shooter);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) shooter, entityLiving);
                    }

                    if(shouldResetHurtTimer)
                    {
                        entityLiving.hurtTime = 0;
                        entityLiving.hurtResistantTime = 0;
                    }

                    bulletHit(entityLiving);

                    //Play hit sound for shooter (arrow ding)
                    if(entityLiving != shooter && entityLiving instanceof EntityPlayer && shooter instanceof EntityPlayerMP)
                        ((EntityPlayerMP) shooter).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                }

                //Play hit sound
                playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

                if(!(entityHit instanceof EntityEnderman))
                    setDead();
            }
            else
            {
                //Couldn't hit entity
                setDead();
            }
        }
        else
        {
            //Hit a block
            playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            BlockPos pos = ray.getBlockPos();
            IBlockState state = worldObj.getBlockState(pos);
            if(state.getMaterial() != Material.AIR)
                state.getBlock().onEntityCollidedWithBlock(worldObj, pos, state, this);
            setDead();
        }
    }

    /**
     * Do anything extra to the entity hit
     */
    protected void bulletHit(EntityLivingBase entityHit) {}

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        //Increase time bullet has been in the air
        ticksInAir++;

        //Ray trace in front of the bullet between its current and next position
        Vec3d posNow = new Vec3d(posX, posY, posZ);
        Vec3d posNext = posNow.addVector(motionX, motionY, motionZ);
        RayTraceResult ray = worldObj.rayTraceBlocks(posNow, posNext, false, true, false);
        posNow = new Vec3d(posX, posY, posZ);
        posNext = posNow.addVector(motionX, motionY, motionZ);
        if(ray != null)
            posNext = ray.hitVec;

        //Get entity hit if one is on the bullet's path
        Entity entityHit = findEntityOnPath(posNow, posNext);
        if(entityHit != null)
        {
            if(entityHit instanceof EntityPlayer && shooter instanceof EntityPlayer && !((EntityPlayer) shooter).canAttackPlayer((EntityPlayer) entityHit))
                ray = null;
            else
                ray = new RayTraceResult(entityHit);
        }

        //If bullet has hit something, call onHit
        if(ray != null)
            onHit(ray);

        //Move bullet
        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        float motionMult = 0.99f;

        //Spawn bubbles while in water
        if(isInWater())
        {
            for(int i = 0; i < 4; ++i)
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25D, posY - motionY * 0.25D, posZ - motionZ * 0.25D, motionX, motionY, motionZ);
            motionMult = 0.6F;
        }

        if(isWet())
            extinguish();

        //Reduce velocity
        motionX *= motionMult;
        motionY *= motionMult;
        motionZ *= motionMult;

        setPosition(posX, posY, posZ);
        doBlockCollisions();
    }

    //Copied from EntityArrow
    @Nullable
    protected Entity findEntityOnPath(Vec3d start, Vec3d end)
    {
        Entity closestEntity = null;
        List<Entity> list = worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expandXyz(1.0D), BULLET_TARGETS);
        double closestDistance = 0.0D;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = list.get(i);

            if (entity != shooter || ticksInAir >= 5)
            {
                AxisAlignedBB aabb = entity.getEntityBoundingBox().expandXyz(0.3D);
                RayTraceResult raytraceresult = aabb.calculateIntercept(start, end);

                if (raytraceresult != null)
                {
                    double distance = start.squareDistanceTo(raytraceresult.hitVec);

                    if (distance < closestDistance || closestDistance == 0.0D)
                    {
                        closestEntity = entity;
                        closestDistance = distance;
                    }
                }
            }
        }

        return closestEntity;
    }
}

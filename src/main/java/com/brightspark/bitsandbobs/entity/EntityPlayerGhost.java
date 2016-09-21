package com.brightspark.bitsandbobs.entity;

import com.brightspark.bitsandbobs.reference.Config;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityPlayerGhost extends EntityLivingBase implements IEntityAdditionalSpawnData
{
    private static final String KEY_AGE = "ghostAge";
    private static final String KEY_SKIN = "ghostSkin";
    private static final int MAX_GHOST_AGE = Config.mirageOrbGhostLife * 20;
    private int ghostAge;
    public ResourceLocation playerSkin;
    public EnumHandSide handSide;

    public EntityPlayerGhost(World world)
    {
        super(world);
        noClip = true;
    }

    public EntityPlayerGhost(World world, EntityPlayer player)
    {
        this(world);
        //Copy position from player
        copyLocationAndAnglesFrom(player);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        ghostAge = tag.getInteger(KEY_AGE);
        playerSkin = new ResourceLocation(tag.getString(KEY_SKIN));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        tag.setInteger(KEY_AGE, ghostAge);
        tag.setString(KEY_SKIN, playerSkin.toString());
    }

    public void onUpdate()
    {
        if(net.minecraftforge.common.ForgeHooks.onLivingUpdate(this)) return;
        onLivingUpdate();
        firstUpdate = false;
    }

    @Override
    public void onLivingUpdate()
    {
        ghostAge++;
        if(!worldObj.isRemote && ghostAge > MAX_GHOST_AGE)
            setDead();
    }

    @Override
    public void onKillCommand()
    {
        this.setDead();
    }

    @Override
    protected void kill()
    {
        this.setDead();
    }

    @Override
    public void knockBack(Entity entityIn, float strenght, double xRatio, double zRatio) {}

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(source.equals(DamageSource.outOfWorld))
        {
            damageEntity(source, amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean isPushedByWater()
    {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList()
    {
        return null;
    }

    @Nullable
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
    {
        return null;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack) {}

    @Override
    public EnumHandSide getPrimaryHand()
    {
        return handSide;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        ByteBufUtils.writeUTF8String(buffer, playerSkin != null ? playerSkin.toString() : "");
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        playerSkin = new ResourceLocation(ByteBufUtils.readUTF8String(additionalData));
    }

    @SideOnly(Side.CLIENT)
    public float getSwingProgress(float partialTickTime)
    {
        return swingProgress;
    }
}

package com.brightspark.bitsandbobs.item.gun;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.item.ItemBasic;
import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMinigun extends ItemBasic implements IGun
{
    private final int minSpeed = 30;
    private final int maxSpeed = 2;
    private final float acceleration = -0.2f;
    private final float deceleration = acceleration * -2;
    private float curSpeed = minSpeed;
    private int lastShotTick = 0;

    //I need to cache the ammo amount here because shooting messes up if I update the stack's NBT
    //So I update the NBT once the player has stopped shooting
    private int ammoCache;
    private boolean isInUse = false;

    public ItemMinigun()
    {
        super("minigun");
        setMaxStackSize(1);
    }
    
    private int getMaxAmmo()
    {
        return getAmmoItem().getMaxAmmo();
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        int ammo = getAmmoAmount(stack);

        if(playerIn.isSneaking())
        {
            if(!worldIn.isRemote && ammo < getMaxAmmo())
                ItemSimpleGun.reloadAll(playerIn, stack);
        }
        else
        {
            if(!playerIn.capabilities.isCreativeMode && ammo == 0)
            {
                //TODO: Play gun out of ammo sound
                return new ActionResult<>(EnumActionResult.PASS, stack);
            }
            //Start shooting
            lastShotTick = 0;
            ammoCache = ammo;
            isInUse = true;
            if(playerIn.capabilities.isCreativeMode)
                curSpeed = maxSpeed;
            playerIn.setActiveHand(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if(player.world.isRemote)
            return;

        //Stop shooting if out of ammo
        if(ammoCache <= 0)
            player.stopActiveHand();

        int ticksUsed = getMaxItemUseDuration(stack) - count;

        //Increase speed
        curSpeed += acceleration;
        if(curSpeed > minSpeed)
            curSpeed = minSpeed;
        else if(curSpeed < maxSpeed)
            curSpeed = maxSpeed;

        //LogHelper.info("Minigun Speed: " + curSpeed + "   Count: " + ticksUsed + "   Next: " + (lastShotTick + curSpeed));

        if(ticksUsed >= lastShotTick + curSpeed)
        {
            //Shoot bullet
            //LogHelper.info("Pew");
            player.world.spawnEntity(new EntityBullet(player.world, player, 5f));
            player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1f, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
            lastShotTick = ticksUsed;
            if(!((EntityPlayer) player).capabilities.isCreativeMode)
                ammoCache--;
        }
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        isInUse = false;
        setAmmoAmount(stack, ammoCache);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn.world.isRemote || !(entityIn instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entityIn;

        if(!player.isHandActive() && curSpeed < minSpeed)
        {
            //Slow down speed when not holding right click
            curSpeed += deceleration;
            if(curSpeed > minSpeed)
                curSpeed = minSpeed;
            //LogHelper.info("Minigun Slowing Down: " + curSpeed);
        }
    }

    @Override
    public void setAmmoAmount(ItemStack stack, int amount)
    {
        NBTHelper.setInteger(stack, "ammo", Math.max(amount, 0));
    }

    @Override
    public int getAmmoAmount(ItemStack stack)
    {
        return NBTHelper.getInt(stack, "ammo");
    }

    @Override
    public int getAmmoSpace(ItemStack stack)
    {
        return getMaxAmmo() - getAmmoAmount(stack);
    }

    @Override
    public IShootable getAmmoItem()
    {
        return BABItems.itemMinigunClip;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        int ammoAmount = isInUse ? ammoCache : getAmmoAmount(stack);
        return 1 - ((float) ammoAmount / (float) getMaxAmmo());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Ammo: " + getAmmoAmount(stack) + "/" + getMaxAmmo());
    }
}

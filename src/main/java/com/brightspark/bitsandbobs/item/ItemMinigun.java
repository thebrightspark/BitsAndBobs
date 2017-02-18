package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.util.LogHelper;
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

public class ItemMinigun extends ItemBasic
{
    protected final int minSpeed = 20;
    protected final int maxSpeed = 2;
    protected final float acceleration = -0.2f;
    protected float curSpeed = minSpeed;
    private int lastShotTick = 0;

    public ItemMinigun(String itemName)
    {
        super(itemName);
        setMaxStackSize(1);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        lastShotTick = 0;
        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    /**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if(player.worldObj.isRemote)
            return;

        int ticksUsed = getMaxItemUseDuration(stack) - count;

        //Increase speed
        curSpeed += acceleration;
        if(curSpeed > minSpeed)
            curSpeed = minSpeed;
        else if(curSpeed < maxSpeed)
            curSpeed = maxSpeed;

        //LogHelper.info("Minigun Speed: " + curSpeed + "   Count: " + ticksUsed + "   Next: " + lastShotTick + curSpeed);

        if(ticksUsed > lastShotTick + curSpeed)
        {
            //Shoot bullet
            //LogHelper.info("Pew");
            player.worldObj.spawnEntityInWorld(new EntityBullet(player.worldObj, player));
            player.worldObj.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1f, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
            lastShotTick = ticksUsed;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn.worldObj.isRemote || !(entityIn instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entityIn;

        if(!player.isHandActive() && curSpeed < minSpeed)
        {
            //Slow down speed when not holding right click
            if(curSpeed < minSpeed)
                curSpeed -= acceleration;
            else
                curSpeed = minSpeed;
            //LogHelper.info("Minigun Slowing Down: " + curSpeed);
        }
    }
}

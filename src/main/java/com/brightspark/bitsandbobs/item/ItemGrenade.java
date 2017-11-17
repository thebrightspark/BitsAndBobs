package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.entity.EntityGrenade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemGrenade extends ItemBasic
{
    public ItemGrenade()
    {
        super("grenade");
        setMaxStackSize(16);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(!player.capabilities.isCreativeMode)
            stack.shrink(1);

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));

        if(!world.isRemote)
            world.spawnEntity(new EntityGrenade(world, player));

        player.addStat(StatList.getObjectUseStats(this));

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}

package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGun extends ItemCooldownBasic
{
    //TODO: Make model/texture for this and the bullets

    public ItemGun(String itemName, int maxCooldown)
    {
        super(itemName, maxCooldown);
    }

    /**
     * This will get called by onItemRightClick when the cooldown is 0
     * @return True if the cooldown should be started.
     */
    protected boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        //Shoot!
        //TODO: Shoot an EntityBullet
        LogHelper.info("Bang.");
        return true;
    }
}

package com.brightspark.bitsandbobs.item;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemEntityNoDespawn extends ItemBasic
{
    public ItemEntityNoDespawn()
    {
        super("entityNoDespawn");
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        if(!(target instanceof EntityLiving) || ((EntityLiving) target).isNoDespawnRequired())
            return false;
        ((EntityLiving) target).enablePersistence();
        stack.stackSize--;
        return true;
    }
}

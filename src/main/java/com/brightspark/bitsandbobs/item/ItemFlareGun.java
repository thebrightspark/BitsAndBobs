package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFlareGun extends ItemCooldownBasic
{
    protected final String KEY_LOADED = "loaded";

    public ItemFlareGun()
    {
        super(Names.Items.FLARE_GUN, 40);
        setMaxStackSize(1);
    }

    @Override
    public boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        boolean isLoaded = NBTHelper.getBoolean(stack, KEY_LOADED);

        //Only run the code on the server
        if(world.isRemote)
            return false;

        if(!player.isSneaking() && isLoaded && !isActive(stack))
        {
            //Spawn effect
            Common.spawnFlareEffect(world, player);
            NBTHelper.setBoolean(stack, KEY_LOADED, false);
            return false;
        }
        if(player.isSneaking() && !isLoaded && player.inventory.hasItemStack(new ItemStack(BABItems.itemFlareAmmo)))
        {
            //Reload
            player.inventory.clearMatchingItems(BABItems.itemFlareAmmo, -1, 1, null);
            NBTHelper.setBoolean(stack, KEY_LOADED, true);
            return true;
        }
        return false;
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return super.showDurabilityBar(stack) || !NBTHelper.getBoolean(stack, KEY_LOADED);
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        return !NBTHelper.getBoolean(stack, KEY_LOADED) && NBTHelper.getInt(stack, KEY_COOLDOWN) == 0 ? 1 : super.getDurabilityForDisplay(stack);
    }
}

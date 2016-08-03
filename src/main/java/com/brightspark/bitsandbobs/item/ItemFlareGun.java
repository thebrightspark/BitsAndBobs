package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.util.Common;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFlareGun extends ItemCooldownBasic
{
    public ItemFlareGun()
    {
        super(Names.Items.FLARE_GUN, 40);
        setMaxStackSize(1);
    }

    @Override
    public void doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        //Spawn effect
        if(world.isRemote)
            Common.spawnFlareEffect(world, player);
    }
}

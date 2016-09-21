package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.init.BABItems;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler
{
    private static final String[] MOBS = {"Creeper", "Spider", "Zombie", "PigZombie", "CaveSpider", "Witch"};

    private static boolean isValidMob(String mobName)
    {
        for(String name : MOBS)
            if(mobName.equals(name))
                return true;
        return false;
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event)
    {
        if(event.getSource().getSourceOfDamage() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
            ItemStack heldStack = player.getHeldItemMainhand();
            if(heldStack != null && heldStack.getItem().equals(Items.PRISMARINE_SHARD) && heldStack.stackSize == 1 && isValidMob(EntityList.getEntityString(event.getEntityLiving())))
                player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(BABItems.itemBloodPrismarine));
        }
    }
}

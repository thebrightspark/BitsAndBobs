package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.init.EItemBasic;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityEventHandler
{
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        if(event.getSource().getImmediateSource() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
            ItemStack heldStack = player.getHeldItemMainhand();
            if(heldStack.getItem().equals(Items.PRISMARINE_SHARD) && event.getEntityLiving() instanceof EntityMob)
            {
                heldStack.shrink(1);
                if(heldStack.getCount() <= 0)
                    heldStack = ItemStack.EMPTY;
                player.setHeldItem(EnumHand.MAIN_HAND, heldStack);
                player.inventory.addItemStackToInventory(BABItems.getBasicItem(EItemBasic.BLOOD_PRISMARINE));
            }
        }
    }
}

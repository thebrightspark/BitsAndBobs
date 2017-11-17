package com.brightspark.bitsandbobs.item.gun;

import com.brightspark.bitsandbobs.handler.EnumGuiID;
import com.brightspark.bitsandbobs.item.ItemBasic;
import com.brightspark.bitsandbobs.util.CommonUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ItemAmmoBelt extends ItemBasic
{
    //TODO: Make ammo belt be able to be put in the belt bauble slot

    public ItemAmmoBelt()
    {
        super("ammoBelt");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote && !playerIn.isSneaking())
            CommonUtils.openGui(playerIn, worldIn, EnumGuiID.AMMO_BELT);
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }

    public static ItemStackHandler getInventoryHandler(ItemStack stack)
    {
        return (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new ICapabilitySerializable()
        {
            private ItemStackHandler items = new ItemStackHandler(9);

            @Override
            public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
            {
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
            {
                return hasCapability(capability, facing) ? (T) items : null;
            }

            @Override
            public NBTBase serializeNBT()
            {
                return items.serializeNBT();
            }

            @Override
            public void deserializeNBT(NBTBase nbt)
            {
                items.deserializeNBT((NBTTagCompound) nbt);
            }
        };
    }
}

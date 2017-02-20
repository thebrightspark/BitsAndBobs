package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.util.InventorySimple;
import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.item.ItemAmmoBelt;
import com.brightspark.bitsandbobs.item.ItemBulletClip;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ContainerAmmoBelt extends Container
{
    protected ItemStackHandler itemStackHandler;
    protected IInventory inventory;
    protected int slotInvStart = 9;

    public ContainerAmmoBelt(EntityPlayer player)
    {
        InventoryPlayer inventoryPlayer = player.inventory;
        ItemStack stack;
        //Get the ammo belt
        if(!((stack = player.getHeldItemMainhand()).getItem() instanceof ItemAmmoBelt))
            if(!((stack = player.getHeldItemOffhand()).getItem() instanceof ItemAmmoBelt))
                throw new RuntimeException("No Ammo Belt being held!");

        itemStackHandler = ItemAmmoBelt.getInventoryHandler(stack);
        inventory = new InventorySimple(stack.getDisplayName(), true, 9, 1);

        //Populate inventory with stack from item handler
        for(int i = 0; i < 9; i++)
            inventory.setInventorySlotContents(i, itemStackHandler.getStackInSlot(i));

        //Add slots
        for(int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 18)
            {
                @Override
                public boolean isItemValid(@Nullable ItemStack stack)
                {
                    return stack == null || stack.getItem() instanceof ItemBulletClip;
                }
            });

        //Bind player slots
        int invStartX = 8;
        int invStartY = 50;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, invStartX + j * 18, invStartY + i * 18));

        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventoryPlayer, i, invStartX + i * 18, invStartY + 18 * 3 + 4));
    }

    /**
     * Copy any changes from the inventory to the ItemStackHandler
     */
    @Nullable
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        ItemStack returnStack = super.slotClick(slotId, dragType, clickTypeIn, player);

        if(!player.worldObj.isRemote && slotId >= 0 && slotId < slotInvStart)
            itemStackHandler.setStackInSlot(slotId, inventory.getStackInSlot(slotId));

        return returnStack;
    }

    public boolean isValidStack(ItemStack stack)
    {
        return stack == null || stack.getItem() == BABItems.itemBulletClip;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return inventory.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = this.inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //If item inventory slot
            if (slot < slotInvStart)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+36, true))
                    return null;

                slotObject.onSlotChange(stackInSlot, stack);
            }
            //If player inventory slot
            else if (slot >= slotInvStart && slot <= slotInvStart+36 && isValidStack(stackInSlot))
            {
                if (!this.mergeItemStack(stackInSlot, 0, slotInvStart, false))
                    return null;
            }

            if (stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();

            if (stackInSlot.stackSize == stack.stackSize)
                return null;

            slotObject.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, inventory);
    }

    /**
     * Called when the container is closed.
     * Not needed atm, but I've added it anyway just in-case.
     */
    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        if(inventory != null)
            inventory.closeInventory(playerIn);
    }
}

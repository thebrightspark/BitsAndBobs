package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.tileentity.BABTileEntity;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by Mark on 28/06/2016.
 */
public class ContainerBlockTrash extends BABContainer
{
    public ContainerBlockTrash(InventoryPlayer invPlayer, BABTileEntity tileEntity)
    {
        super(invPlayer, tileEntity);
    }

    protected void init()
    {
        invStartY = 81;
    }

    @Override
    protected void addSlots()
    {
        //Adds the input slot which you can't remove items from, but can input.
        addSlotToContainer(new Slot(inventory, 0, 80, 22)
        {
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
            public void onSlotChanged()
            {
                this.inventory.markDirty();
                ContainerBlockTrash.this.onCraftMatrixChanged(this.inventory);
            }
        });
        //Adds the 9 slots which you can't put items into, but can remove.
        for(int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(inventory, x + 1, 8 + (x * 18), 49)
            {
                public boolean isItemValid(@Nullable ItemStack stack)
                {
                    return false;
                }
                public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
                {
                    this.onSlotChanged();
                    ContainerBlockTrash.this.onCraftMatrixChanged(this.inventory);
                }
            });
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     * Using this to update the stacks when a player has put a new stack in or taken one out.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        ((TileTrash)inventory).updateInventory();
        detectAndSendChanges();
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

            //If slot 1-9 (trash inventory)
            if (slot > 0 && slot < slotInvStart)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+36, false))
                    return null;
                slotObject.onSlotChange(stackInSlot, stack);
            }
            //If slot Inventory
            else if (slot >= slotInvStart && slot <= slotInvStart+36 && inventory.isItemValidForSlot(0, stackInSlot))
            {
                if (!this.mergeItemStack(stackInSlot, 0, 1, false))
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
}

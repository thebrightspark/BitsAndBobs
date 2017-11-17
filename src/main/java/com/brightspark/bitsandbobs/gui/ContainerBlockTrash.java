package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.tileentity.BABTileInventory;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by Mark on 28/06/2016.
 */
public class ContainerBlockTrash extends BABContainerInventory
{
    public ContainerBlockTrash(InventoryPlayer invPlayer, BABTileInventory tileEntity)
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
                super.onSlotChanged();
                ((TileTrash) this.inventory).addItem();
                ContainerBlockTrash.this.detectAndSendChanges();
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
                public ItemStack onTake(EntityPlayer playerIn, ItemStack stack)
                {
                    ItemStack returnStack = super.onTake(playerIn, stack);
                    ContainerBlockTrash.this.detectAndSendChanges();
                    return returnStack;
                }
            });
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slotObject = this.inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //If slot 1-9 (trash inventory)
            if (slot > 0 && slot < slotInvStart)
            {
                if (!this.mergeItemStack(stackInSlot, slotInvStart, slotInvStart+36, false))
                    return ItemStack.EMPTY;
                slotObject.onSlotChange(stackInSlot, stack);
            }
            //If slot Inventory
            else if (slot >= slotInvStart && slot <= slotInvStart+36 && inventory.isItemValidForSlot(0, stackInSlot))
            {
                if (!this.mergeItemStack(stackInSlot, 0, 1, false))
                    return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0)
                slotObject.putStack(ItemStack.EMPTY);
            else
                slotObject.onSlotChanged();

            if (stackInSlot.getCount() == stack.getCount())
                return ItemStack.EMPTY;

            slotObject.onTake(player, stackInSlot);
        }

        return stack;
    }
}

package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.tileentity.BABTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBlockHealing extends BABContainer
{
    private int fuel;

    public ContainerBlockHealing(InventoryPlayer invPlayer, BABTileEntity tileEntity)
    {
        super(invPlayer, tileEntity);
    }

    @Override
    protected void addSlots()
    {
        addSlotToContainer(new Slot(inventory, 0, 79, 19)
        {
            public boolean isItemValid(ItemStack stack)
            {
                return inventory.isItemValidForSlot(slotNumber, stack);
            }
        });
    }

    /**
     * Called by transferStackInSlot for an easy way to set valid items without overriding the whole method
     */
    @Override
    public boolean isValidStack(ItemStack stack)
    {
        return inventory.isItemValidForSlot(0, stack);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);
            if (this.fuel != this.inventory.getField(0))
                icontainerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
        }

        this.fuel = this.inventory.getField(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.inventory.setField(id, data);
    }
}

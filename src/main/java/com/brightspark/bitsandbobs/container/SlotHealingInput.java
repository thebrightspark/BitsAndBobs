package com.brightspark.bitsandbobs.container;

import com.brightspark.bitsandbobs.tileentity.TileHealing;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHealingInput extends Slot
{
    public SlotHealingInput(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem().equals(TileHealing.inputItem);
    }
}

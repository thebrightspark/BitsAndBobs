package com.brightspark.bitsandbobs.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class InventorySimple implements IInventory
{
    private String title;
    private final boolean hasCustomName;
    private final int slotCount, stackLimit;
    private final ItemStack[] contents;

    public InventorySimple(String title, boolean hasCustomName, int slotCount, int stackLimit)
    {
        this.title = title;
        this.hasCustomName = hasCustomName;
        this.slotCount = slotCount;
        this.stackLimit = stackLimit;
        this.contents = new ItemStack[slotCount];
    }

    public ItemStack addItem(ItemStack stack)
    {
        ItemStack itemstack = stack.copy();

        for(int i = 0; i < slotCount; ++i)
        {
            ItemStack itemstack1 = getStackInSlot(i);

            if(itemstack1 == null && isItemValidForSlot(i, itemstack))
            {
                setInventorySlotContents(i, itemstack);
                markDirty();
                return null;
            }

            if(ItemStack.areItemsEqual(itemstack1, itemstack))
            {
                int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
                int k = Math.min(itemstack.stackSize, j - itemstack1.stackSize);

                if(k > 0)
                {
                    itemstack1.stackSize += k;
                    itemstack.stackSize -= k;

                    if(itemstack.stackSize <= 0)
                    {
                        markDirty();
                        return null;
                    }
                }
            }
        }

        if(itemstack.stackSize != stack.stackSize)
            markDirty();

        return itemstack;
    }

    @Override
    public int getSizeInventory()
    {
        return slotCount;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return index >= 0 && index < slotCount ? contents[index] : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemStack = ItemStackHelper.getAndSplit(contents, index, count);

        if(itemStack != null)
            markDirty();

        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if (contents[index] != null)
        {
            ItemStack itemstack = contents[index];
            contents[index] = null;
            return itemstack;
        }
        else
            return null;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        contents[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();

        markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return stackLimit;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        for(int i = 0; i < contents.length; ++i)
            contents[i] = null;
    }

    @Override
    public String getName()
    {
        return title;
    }

    @Override
    public boolean hasCustomName()
    {
        return hasCustomName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
    }
}

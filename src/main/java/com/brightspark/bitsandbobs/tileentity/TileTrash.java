package com.brightspark.bitsandbobs.tileentity;

import com.brightspark.bitsandbobs.gui.ContainerBlockTrash;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;

public class TileTrash extends BABTileEntity implements ISidedInventory, IInteractionObject
{
    private final String KEY_INVENTORY = "inventory";
    private final String KEY_SLOT = "slot";
    private int[] allSlots = new int[] {0,1,2,3,4,5,6,7,8,9};
    protected ItemStack[] stacks = new ItemStack[10];

    public TileTrash() {}

    /**
     * Called from the gui when the inventory is changed.
     */
    public void updateInventory()
    {
        if(stacks[0] != null)
            inputItem();
        else
            shuffleItemsUp();
    }

    /**
     * Adds the stack from the input slot to the main slots.
     */
    private void inputItem()
    {
        shuffleItemsDown();
        stacks[1] = stacks[0].copy();
        stacks[0] = null;
    }

    /**
     * Moves all stacks right one to allow for new stack.
     */
    private void shuffleItemsDown()
    {
        for(int i = 9; i > 0; i--)
        {
            if(stacks[i] == null)
                continue;
            if(i < 9)
                stacks[i + 1] = stacks[i].copy();
            stacks[i] = null;
        }
    }

    /**
     * Moves stacks to the left to fill any gaps created.
     */
    private void shuffleItemsUp()
    {
        for(int i = 1; i <= 9; i++)
        {
            if(stacks[i] != null && stacks[i].stackSize == 0)
                stacks[i] = null;
            if(stacks[i] == null)
                for(int nextI = i + 1; nextI <= 9; nextI++)
                {
                    if(stacks[nextI] != null)
                    {
                        if(stacks[nextI].stackSize == 0)
                        {
                            stacks[nextI] = null;
                            continue;
                        }
                        stacks[i] = stacks[nextI].copy();
                        stacks[nextI] = null;
                        break;
                    }
                }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        stacks = new ItemStack[10];
        NBTTagList nbttaglist = tag.getTagList(KEY_INVENTORY, 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte(KEY_SLOT) & 255;
            if (j >= 0 && j < stacks.length)
                stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.stacks.length; ++i)
        {
            if (this.stacks[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte(KEY_SLOT, (byte)i);
                stacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        tag.setTag(KEY_INVENTORY, nbttaglist);
        return tag;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return allSlots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return index == 0;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return index > 0 && index <= 10;
    }

    @Override
    public int getSizeInventory()
    {
        return 10;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return stacks[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack stack = (index > 0 && index <= 10) && stacks[index] != null && count >= stacks[index].stackSize ? stacks[index].copy() : null;
        stacks[index] = null;
        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if(index < 1 || index > 10 || stacks[index] == null) return null;
        ItemStack stack = stacks[index].copy();
        stacks[index] = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        if(index == 0 && stack != null)
            stacks[0] = stack;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == 0 && stack != null;
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
        stacks = new ItemStack[10];
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerBlockTrash(playerInventory, this);
    }

    @Override
    public String getGuiID()
    {
        return Reference.MOD_ID + ":" + this.blockType.getUnlocalizedName().substring(6);
    }
}

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileTrash extends BABTileEntity implements ISidedInventory, IInteractionObject
{
    private final String KEY_INVENTORY = "inventory";
    private final String KEY_SLOT = "slot";
    private int[] allSlots = new int[] {0,1,2,3,4,5,6,7,8,9};
    protected ItemStack inputStack;
    protected List<ItemStack> stacks = new ArrayList<ItemStack>(9);

    public TileTrash() {}

    private void removeNulls()
    {
        //Remove any null values
        Iterator<ItemStack> iterator = stacks.iterator();
        while(iterator.hasNext())
            if(iterator.next() == null)
                iterator.remove();
        //Remove any values at indices greater than 8 (keeping 9 values)
        while(stacks.size() > 9)
            stacks.remove(9);
    }

    public void addItem()
    {
        if(inputStack == null) return;
        stacks.add(0, inputStack.copy());
        inputStack = null;
        removeNulls();
    }

    private boolean isInputSlot(int index)
    {
        return index == 0;
    }

    private boolean isOutputSlot(int index)
    {
        return index > 0 && index < 10;
    }

    private ItemStack getStack(int index)
    {
        return stacks.size() > index ? stacks.get(index) : null;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        inputStack = tag.hasKey("id") ? ItemStack.loadItemStackFromNBT(tag) : null;

        stacks.clear();
        NBTTagList nbttaglist = tag.getTagList(KEY_INVENTORY, 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int slotNum = nbttagcompound.getByte(KEY_SLOT);
            stacks.set(slotNum, ItemStack.loadItemStackFromNBT(nbttagcompound));
        }
        removeNulls();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        if(inputStack != null)
            inputStack.writeToNBT(tag);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < stacks.size(); ++i)
        {
            if (stacks.get(i) != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte(KEY_SLOT, (byte)i);
                stacks.get(i).writeToNBT(nbttagcompound);
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
        return isInputSlot(index);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return isOutputSlot(index) && stacks.get(index).isItemEqual(stack);
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
        return isOutputSlot(index) ? getStack(index) : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if(!isOutputSlot(index) || stacks.get(index) == null) return null;
        ItemStack stack = getStack(index);
        ItemStack returnStack = stack.splitStack(count);
        if(stack.stackSize <= 0)
            stacks.remove(index);
        else
            stacks.set(index, stack);
        return returnStack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if(!isOutputSlot(index) || stacks.get(index) == null) return null;
        ItemStack stack = getStack(index - 1);
        if(stack != null) stack = stack.copy();
        stacks.remove(index - 1);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        if(!isInputSlot(index) || !isOutputSlot(index)) return;
        stacks.set(index, stack);
        removeNulls();
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
        return isInputSlot(index) && stack != null;
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
        stacks.clear();
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

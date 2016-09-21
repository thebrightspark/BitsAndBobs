package com.brightspark.bitsandbobs.tileentity;

import com.brightspark.bitsandbobs.container.ContainerBlockHealing;
import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.reference.Reference;
import com.brightspark.bitsandbobs.util.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;

import java.util.List;

public class TileHealing extends BABTileEntity implements ITickable, IInteractionObject
{
    private static final String KEY_FUEL = "fuel";
    private static final String KEY_FUEL_MAX = "fuelMax";
    private static final String KEY_TICKS = "ticks";
    private static final String KEY_HAS_ITEM = "hasItem";

    private ItemStack inputStack;
    private boolean addingFuel = false;
    private int ticks;
    private int fuelMax;
    private int fuel = 0;

    public TileHealing() {}

    public void setFuelMax(int fuelMax)
    {
        this.fuelMax = fuelMax;
    }

    public void setTicksBetweenChecks(int ticks)
    {
        this.ticks = ticks;
    }

    /**
     * Returns whether the given stack is a valid input for this block
     */
    @Override
    public boolean isValidItem(ItemStack stack)
    {
        return getFuelForItem(stack) > 0;
    }

    public static int getFuelForItem(ItemStack stack)
    {
        if(Config.healingBlockValidFuelStacks.containsKey(stack))
            return Config.healingBlockValidFuelStacks.get(stack);
        for(ItemStack configStack : Config.healingBlockValidFuelStacks.keySet())
            if(configStack.isItemEqual(stack))
                return Config.healingBlockValidFuelStacks.get(configStack);
        return 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        fuel = tag.getInteger(KEY_FUEL);
        fuelMax = tag.getInteger(KEY_FUEL_MAX);
        ticks = tag.getInteger(KEY_TICKS);
        if(tag.hasKey(KEY_HAS_ITEM))
            inputStack = ItemStack.loadItemStackFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger(KEY_FUEL, fuel);
        tag.setInteger(KEY_FUEL_MAX, fuelMax);
        tag.setInteger(KEY_TICKS, ticks);
        if(inputStack != null)
        {
            tag.setBoolean(KEY_HAS_ITEM, true);
            inputStack.writeToNBT(tag);
        }
        return tag;
    }

    @Override
    public void update()
    {
        if(addingFuel && !worldObj.isRemote)
        {
            if(inputStack != null && inputStack.stackSize > 0)
            {
                int fuelToAdd = getFuelForItem(inputStack);
                if(fuel + fuelToAdd <= fuelMax)
                {
                    fuel += fuelToAdd;
                    inputStack.stackSize--;
                }
            }
            else
                addingFuel = false;
            if(inputStack != null && inputStack.stackSize <= 0)
                inputStack = null;
        }

        if(worldObj.getTotalWorldTime() % ticks == 0) //Check every so many ticks (20 ticks for first healing block)
        {
            //Check fuel slot
            if(!worldObj.isRemote && inputStack != null && inputStack.stackSize > 0)
                addingFuel = true;

            //Heal players!
            BlockPos pos = this.getPos().up(); //Block above this healing block
            AxisAlignedBB area = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, area);
            for(EntityPlayer p : players)
            {
                //Check to see if player is standing in blockspace above block and if need healing
                if(fuel > 0 && p.shouldHeal() && !p.capabilities.isCreativeMode)
                {
                    if(worldObj.isRemote)
                        ClientUtils.spawnTwirlEffect(worldObj, p);
                    else
                    {
                        p.heal(2); //Heal 1 heart
                        fuel--; //Reduce fuel in block
                    }
                }
            }
        }

        this.markDirty();
        worldObj.scheduleUpdate(this.getPos(), this.getBlockType(), 2);
    }

    public int getFuelAmount()
    {
        return fuel;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.UP ? new int[0] : new int[]{0};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return index == 0;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return index == 0 ? inputStack : null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if(index == 0 && inputStack != null)
        {
            if(inputStack.stackSize <= count) //If trying to take all or more of items in slot
                return removeStackFromSlot(index);
            else //If needing to split the stack
            {
                ItemStack stack = inputStack.splitStack(count);
                if(inputStack.stackSize == 0)
                    inputStack = null;
                return stack;
            }
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        if(index == 0 && inputStack != null)
        {
            ItemStack stack = ItemStack.copyItemStack(inputStack);
            inputStack = null;
            return stack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        if(index == 0)
            inputStack = stack;
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
        return index == 0 && isValidItem(stack);
    }

    @Override
    public int getField(int id)
    {
        return id == 0 ? fuel : 0;
    }

    @Override
    public void setField(int id, int value)
    {
        if(id == 0) fuel = value;
    }

    @Override
    public int getFieldCount()
    {
        return 1;
    }

    @Override
    public void clear()
    {
        inputStack = null;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerBlockHealing(playerInventory, this);
    }

    @Override
    public String getGuiID()
    {
        return Reference.MOD_ID + ":" + this.blockType.getUnlocalizedName().substring(6);
    }
}

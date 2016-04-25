package com.brightspark.bitsandbobs.tileentity;

import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.reference.Reference;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;

import java.util.List;

public class TileHealing extends TileEntity implements ITickable, ISidedInventory
{
    public static Item inputItem = Items.golden_apple;

    private static final int COOLDOWN_MAX = 20;
    private static final String KEY_COOLDOWN = "cooldown";
    private int cooldown = 0;
    private static final String KEY_FUEL = "fuel";
    private int fuel = 0;

    public TileHealing() {}

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        if(tag.hasKey(KEY_COOLDOWN))
            cooldown = tag.getInteger(KEY_COOLDOWN);
        if(tag.hasKey(KEY_FUEL))
            fuel = tag.getInteger(KEY_FUEL);
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger(KEY_FUEL, fuel);
        tag.setInteger(KEY_COOLDOWN, cooldown);
    }

    /**
     * Use this to send data about the block. In this case, the NBTTagCompound.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(pos, 0, nbt);
    }

    /**
     * Use this to update the block when a packet is received.
     */
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void update()
    {
        if(cooldown == 0)
        {
            //Cooldown is always being reset - this way it's only checking for players once per second
            cooldown = COOLDOWN_MAX;

            //Heal players!
            BlockPos pos = this.getPos().up(); //Block above this healing block
            AxisAlignedBB area = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, area);
            for(EntityPlayer p : players)
            {
                LogHelper.info("Player Pos: " + p.getPosition().toString());
                //Check to see if player is standing in blockspace above block and if need healing
                if(p.shouldHeal())
                {
                    LogHelper.info(">>>> Healing: " + p.getDisplayNameString());
                    p.heal(2); //Heal 1 heart

                }
            }
        }

        if(cooldown > 0)
            cooldown--;

        this.markDirty();
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public IChatComponent getDisplayName()
    {
        return null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {

    }

    @Override
    public int getInventoryStackLimit()
    {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return false;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }
}

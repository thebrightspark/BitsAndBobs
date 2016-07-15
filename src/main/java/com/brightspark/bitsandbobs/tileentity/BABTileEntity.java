package com.brightspark.bitsandbobs.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

/**
 * Created by Mark on 28/06/2016.
 */
public abstract class BABTileEntity extends TileEntity implements ISidedInventory
{
    protected final String KEY_DEFAULT_NAME = "defaultName";
    protected final String KEY_CUSTOM_NAME = "customName";
    protected String defaultName = "";
    protected String customName;

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        defaultName = tag.getString(KEY_DEFAULT_NAME);
        if(tag.hasKey(KEY_CUSTOM_NAME))
            customName = tag.getString(KEY_CUSTOM_NAME);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setString(KEY_DEFAULT_NAME, defaultName);
        if(hasCustomName())
            tag.setString(KEY_CUSTOM_NAME, customName);
        return tag;
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    /**
     * Use this to send data about the block. In this case, the NBTTagCompound.
     */
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
    }

    /**
     * Use this to update the block when a packet is received.
     */
    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    public boolean isValidItem(ItemStack stack)
    {
        return true;
    }

    public void setDefaultName(String name)
    {
        defaultName = name;
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? customName : defaultName;
    }

    @Override
    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public void setName(String name)
    {
        customName = name;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
}

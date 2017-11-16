package com.brightspark.bitsandbobs.tileentity;

import com.brightspark.bitsandbobs.util.CommonUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class BABTileInventory extends BABTileEntity implements ISidedInventory
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
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return CommonUtils.isUsableByPlayer(this, player);
    }
}

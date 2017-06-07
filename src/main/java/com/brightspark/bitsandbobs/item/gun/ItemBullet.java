package com.brightspark.bitsandbobs.item.gun;

import com.brightspark.bitsandbobs.item.ItemBasic;

public class ItemBullet extends ItemBasic implements IShootable
{
    public ItemBullet(String itemName)
    {
        super(itemName);
    }

    public ItemBullet(String itemName, int maxStackSize)
    {
        super(itemName);
        setMaxStackSize(maxStackSize);
    }

    @Override
    public boolean isClip()
    {
        return false;
    }

    @Override
    public int getMaxAmmo()
    {
        return maxStackSize;
    }
}

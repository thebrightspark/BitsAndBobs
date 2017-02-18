package com.brightspark.bitsandbobs.item;

import net.minecraft.item.ItemStack;

public interface IUseAmmo
{
    void setAmmoAmount(ItemStack stack, int amount);

    int getAmmoAmount(ItemStack stack);

    int getAmmoSpace(ItemStack stack);
}

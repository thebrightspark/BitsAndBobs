package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBulletClip extends ItemBasic
{
    //TODO: Make texture change depending on how full it is?
    public int clipSize;

    public ItemBulletClip(String itemName, int clipSize)
    {
        super(itemName);
        setMaxStackSize(1);
        this.clipSize = clipSize;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        //Add empty and full clips
        ItemStack clip = new ItemStack(itemIn);
        setBulletsAmount(clip, 0);
        subItems.add(clip.copy());
        setBulletsAmount(clip, clipSize);
        subItems.add(clip);
    }

    public static boolean isClipWithAmmo(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemBulletClip && getBulletsAmount(stack) > 0;
    }

    public static void setBulletsAmount(ItemStack stack, int amount)
    {
        NBTHelper.setInteger(stack, "ammo", Math.max(Math.min(amount, ((ItemBulletClip) stack.getItem()).clipSize), 0));
    }

    public static int getBulletsAmount(ItemStack stack)
    {
        return NBTHelper.getInt(stack, "ammo");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Bullets: " + getBulletsAmount(stack) + "/" + clipSize);
    }
}

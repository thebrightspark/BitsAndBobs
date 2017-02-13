package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Mark on 12/02/2017.
 */
public class ItemBulletClip extends ItemBasic
{
    //TODO: Make texture change depending on how full it is?
    public static int CLIP_SIZE = 10;

    public ItemBulletClip(String itemName)
    {
        super(itemName);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        //Add empty and full clips
        ItemStack clip = new ItemStack(itemIn);
        setBulletsAmount(clip, 0);
        subItems.add(clip.copy());
        setBulletsAmount(clip, CLIP_SIZE);
        subItems.add(clip);
    }

    public static void setBulletsAmount(ItemStack stack, int amount)
    {
        NBTHelper.setInteger(stack, "ammo", Math.max(Math.min(amount, CLIP_SIZE), 0));
    }

    public static int getBulletsAmount(ItemStack stack)
    {
        return NBTHelper.getInt(stack, "ammo");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Bullets: " + getBulletsAmount(stack) + "/" + CLIP_SIZE);
    }
}

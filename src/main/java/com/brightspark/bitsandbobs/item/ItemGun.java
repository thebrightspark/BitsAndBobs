package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.util.LogHelper;
import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemGun extends ItemCooldownBasic
{
    //TODO: Make model/texture for this and the bullets

    public ItemGun(String itemName)
    {
        super(itemName, 10, false);
    }

    /**
     * This will get called by onItemRightClick when the cooldown is 0
     * @return True if the cooldown should be started.
     */
    @Override
    protected boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if(world.isRemote)
            return true;

        int ammo = getAmmoAmount(stack);

        if(player.isSneaking())
        {
            //Reload
            if(ammo < ItemBulletClip.CLIP_SIZE)
            {
                reloadAll(player, stack);
                return true;
            }
            return false;
        }
        else
        {
            if(ammo == 0)
            {
                //TODO: Play gun out of ammo sound
                return false;
            }
            //Shoot
            world.spawnEntityInWorld(new EntityBullet(world, player));
            setAmmoAmount(stack, --ammo);
            return true;
        }
    }

    /**
     * Reloads the gun as much as possible using any ammo clips in the player's inventory
     */
    private void reloadAll(EntityPlayer player, ItemStack gunStack)
    {
        //Check held items first
        for(EnumHand hand : EnumHand.values())
        {
            ItemStack heldStack = player.getHeldItem(hand);
            if(isClipWithAmmo(heldStack) && reloadWithClip(gunStack, heldStack) == 0)
                return;
        }

        //Check main inventory
        for(int i = 0; i < player.inventory.mainInventory.length; i++)
        {
            ItemStack stack = player.inventory.mainInventory[i];
            if(isClipWithAmmo(stack) && reloadWithClip(gunStack, stack) == 0)
                return;
        }
    }

    public static int reloadWithClip(ItemStack gunStack, ItemStack clipStack)
    {
        int bulletsInClip = ItemBulletClip.getBulletsAmount(clipStack);
        int remaining = reload(gunStack, bulletsInClip);
        ItemBulletClip.setBulletsAmount(clipStack, remaining);
        return getAmmoSpace(gunStack);
    }

    /**
     * Reloads the gun with the given amount of bullets
     * Returns how many bullets weren't used of the amount given
     */
    public static int reload(ItemStack gunStack, int bullets)
    {
        int spaceInGun = getAmmoSpace(gunStack);
        if(spaceInGun == 0 || bullets == 0)
            return bullets;
        int toRefill = Math.min(spaceInGun, bullets);
        setAmmoAmount(gunStack, getAmmoAmount(gunStack) + toRefill);
        return bullets - toRefill;
    }

    protected boolean isClipWithAmmo(ItemStack stack)
    {
        return stack != null && stack.getItem() == BABItems.itemBulletClip && ItemBulletClip.getBulletsAmount(stack) > 0;
    }

    protected static void setAmmoAmount(ItemStack stack, int amount)
    {
        NBTHelper.setInteger(stack, "ammo", Math.max(amount, 0));
    }

    public static int getAmmoAmount(ItemStack stack)
    {
        return NBTHelper.getInt(stack, "ammo");
    }

    public static int getAmmoSpace(ItemStack stack)
    {
        return ItemBulletClip.CLIP_SIZE - getAmmoAmount(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1 - ((float) getAmmoAmount(stack) / (float) ItemBulletClip.CLIP_SIZE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Ammo: " + getAmmoAmount(stack) + "/" + ItemBulletClip.CLIP_SIZE);
    }
}

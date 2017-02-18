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
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class ItemPistol extends ItemCooldownBasic implements IUseAmmo
{
    //TODO: Make model/texture for this and the bullets

    public static final int MAX_AMMO = 10;

    public ItemPistol()
    {
        super("itemGun", 10, false);
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
            if(ammo < MAX_AMMO)
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
    public static void reloadAll(EntityPlayer player, ItemStack gunStack)
    {
        if(gunStack == null || !(gunStack.getItem() instanceof IUseAmmo))
        {
            LogHelper.error("Trying to reload an ItemStack that is null of it's Item isn't an instance of IUseAmmo!");
            return;
        }

        //Check held items first
        for(EnumHand hand : EnumHand.values())
        {
            ItemStack heldStack = player.getHeldItem(hand);
            if(ItemBulletClip.isClipWithAmmo(heldStack) && reloadWithClip(gunStack, heldStack) == 0)
                return;
        }

        //Check any ammo belts before any loose ones in inventory
        for(int i = 0; i < player.inventory.mainInventory.length; i++)
        {
            //Find ammo belts
            ItemStack stack = player.inventory.mainInventory[i];
            if(stack != null && stack.getItem() == BABItems.itemAmmoBelt)
            {
                //Find ammo clips within the ammo belt
                ItemStackHandler ammoHandler = ItemAmmoBelt.getInventoryHandler(stack);
                for(int j = 0; j < ammoHandler.getSlots(); j++)
                {
                    ItemStack ammoClip = ammoHandler.getStackInSlot(j);
                    if(ItemBulletClip.isClipWithAmmo(ammoClip) && reloadWithClip(gunStack, ammoClip) == 0)
                        return;
                }
            }
        }

        //Check main inventory
        for(int i = 0; i < player.inventory.mainInventory.length; i++)
        {
            ItemStack stack = player.inventory.mainInventory[i];
            if(ItemBulletClip.isClipWithAmmo(stack) && reloadWithClip(gunStack, stack) == 0)
                return;
        }
    }

    /**
     * Reloads the gun with the given clip stack
     * Returns how many bullets weren't used of the amount given
     */
    public static int reloadWithClip(ItemStack gunStack, ItemStack clipStack)
    {
        int bulletsInClip = ItemBulletClip.getBulletsAmount(clipStack);
        if((clipStack.getItem() == BABItems.itemBulletClip && gunStack.getItem() instanceof ItemPistol) ||
                (clipStack.getItem() == BABItems.itemMinigunClip && gunStack.getItem() instanceof ItemMinigun))
        {
            int remaining = reload(gunStack, bulletsInClip);
            ItemBulletClip.setBulletsAmount(clipStack, remaining);
            return ((IUseAmmo) gunStack.getItem()).getAmmoSpace(gunStack);
        }
        return bulletsInClip;
    }

    /**
     * Reloads the gun with the given amount of bullets
     * Returns how many bullets weren't used of the amount given
     */
    public static int reload(ItemStack gunStack, int bullets)
    {
        int spaceInGun = ((IUseAmmo) gunStack.getItem()).getAmmoSpace(gunStack);
        if(spaceInGun == 0 || bullets == 0)
            return bullets;
        int toRefill = Math.min(spaceInGun, bullets);
        ((IUseAmmo) gunStack.getItem()).setAmmoAmount(gunStack, ((IUseAmmo) gunStack.getItem()).getAmmoAmount(gunStack) + toRefill);
        return bullets - toRefill;
    }

    @Override
    public void setAmmoAmount(ItemStack stack, int amount)
    {
        NBTHelper.setInteger(stack, "ammo", Math.max(amount, 0));
    }

    @Override
    public int getAmmoAmount(ItemStack stack)
    {
        return NBTHelper.getInt(stack, "ammo");
    }

    @Override
    public int getAmmoSpace(ItemStack stack)
    {
        return MAX_AMMO - getAmmoAmount(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1 - ((float) getAmmoAmount(stack) / (float) MAX_AMMO);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("Ammo: " + getAmmoAmount(stack) + "/" + MAX_AMMO);
    }
}

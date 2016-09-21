package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.util.ClientUtils;
import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFlareGun extends ItemCooldownBasic
{
    protected final String KEY_LOADED = "loaded";

    public ItemFlareGun()
    {
        super(Names.Items.FLARE_GUN, 40);
    }

    @Override
    public boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        boolean isLoaded = NBTHelper.getBoolean(stack, KEY_LOADED);

        if(!player.isSneaking() && isLoaded && !isActive(stack))
        {
            //Spawn effect
            if(world.isRemote)
                ClientUtils.spawnFlareEffect(world, player);
            if(!player.isCreative())
                NBTHelper.setBoolean(stack, KEY_LOADED, false);
            return false;
        }
        if(player.isSneaking() && !isLoaded && player.inventory.hasItemStack(new ItemStack(BABItems.itemFlareAmmo)))
        {
            //Reload
            player.inventory.clearMatchingItems(BABItems.itemFlareAmmo, -1, 1, null);
            NBTHelper.setBoolean(stack, KEY_LOADED, true);
            return true;
        }
        return false;
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return !NBTHelper.getBoolean(stack, KEY_LOADED) || super.showDurabilityBar(stack);
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        return !NBTHelper.getBoolean(stack, KEY_LOADED) && NBTHelper.getInt(stack, KEY_COOLDOWN) == 0 ? 1 : super.getDurabilityForDisplay(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
    {
        list.add(I18n.format(TOOLTIP + "1"));
        list.add(I18n.format(TOOLTIP + "2"));
    }
}

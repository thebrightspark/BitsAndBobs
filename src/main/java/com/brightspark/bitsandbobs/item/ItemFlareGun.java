package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.entity.EntityFlare;
import com.brightspark.bitsandbobs.init.BABItems;
import com.brightspark.bitsandbobs.item.gun.IShootable;
import com.brightspark.bitsandbobs.item.gun.ItemSimpleGun;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemFlareGun extends ItemSimpleGun
{
    public ItemFlareGun()
    {
        super("flareGun", 40);
    }

    @Override
    protected void spawnBullet(World world, EntityPlayer player)
    {
        world.spawnEntity(new EntityFlare(world, player));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
    {
        list.add(I18n.format(TOOLTIP + "1"));
        list.add(I18n.format(TOOLTIP + "2"));
    }

    @Nonnull
    @Override
    public IShootable getAmmoItem()
    {
        return BABItems.itemFlareAmmo;
    }
}

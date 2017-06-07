package com.brightspark.bitsandbobs.item.gun;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.init.BABItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemPistol extends ItemSimpleGun
{
    public ItemPistol()
    {
        super("pistol", 10);
    }

    @Override
    public IShootable getAmmoItem()
    {
        return BABItems.itemPistolClip;
    }

    @Override
    protected void spawnBullet(World world, EntityPlayer player)
    {
        world.spawnEntity(new EntityBullet(world, player).setDamage(6f));
    }
}

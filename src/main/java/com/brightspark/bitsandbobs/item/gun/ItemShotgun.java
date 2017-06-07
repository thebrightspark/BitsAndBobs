package com.brightspark.bitsandbobs.item.gun;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.init.BABItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemShotgun extends ItemSimpleGun
{
    public ItemShotgun()
    {
        super("shotgun", 15);
    }

    @Override
    public IShootable getAmmoItem()
    {
        return BABItems.itemShotgunShell;
    }

    @Override
    protected void spawnBullet(World world, EntityPlayer player)
    {
        for(int i = 0; i < 6; i++)
            world.spawnEntity(new EntityBullet(world, player, 10f));
    }
}

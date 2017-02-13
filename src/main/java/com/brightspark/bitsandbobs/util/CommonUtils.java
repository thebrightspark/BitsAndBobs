package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.handler.EnumGuiID;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by Mark on 12/02/2017.
 */
public class CommonUtils
{
    public static DamageSource causeBulletDamage(EntityBullet bullet, Entity indirectEntity)
    {
        return new EntityDamageSourceIndirect(Reference.MOD_ID + ".bullet", bullet, indirectEntity).setProjectile();
    }

    public static void sortStringList(List<String> list)
    {
        Collections.sort(list);
    }

    /**
     * Open a GUI for a block (Uses a guiID of -1).
     */
    public static void openGui(EntityPlayer player, World world, BlockPos pos)
    {
        player.openGui(BitsAndBobs.instance, -1, world, pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Open a GUI for an item (Uses the position of the player).
     */
    public static void openGui(EntityPlayer player, World world, EnumGuiID guiID)
    {
        openGui(player, world, guiID.ordinal());
    }

    /**
     * Open a GUI for an item (Uses the position of the player).
     */
    public static void openGui(EntityPlayer player, World world, int guiID)
    {
        BlockPos pos = player.getPosition();
        player.openGui(BitsAndBobs.instance, guiID, world, pos.getX(), pos.getY(), pos.getZ());
    }
}

package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

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
}

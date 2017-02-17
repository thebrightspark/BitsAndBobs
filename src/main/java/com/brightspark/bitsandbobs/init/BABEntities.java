package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.entity.EntityBullet;
import com.brightspark.bitsandbobs.entity.EntityPlayerGhost;
import com.brightspark.bitsandbobs.entity.RenderBullet;
import com.brightspark.bitsandbobs.entity.RenderPlayerGhost;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABEntities
{
    private static List<Class<? extends Entity>> ENTITY_CLASSES = new ArrayList<Class<? extends Entity>>();
    private static int modEntityId = 0;

    private static void regEntity(Class<? extends Entity> entityClass, String name)
    {
        EntityRegistry.registerModEntity(entityClass, name, ++modEntityId, BitsAndBobs.instance, 64, 1, false);
        ENTITY_CLASSES.add(entityClass);
    }

    public static void regEntities()
    {
        regEntity(EntityPlayerGhost.class, "PlayerGhost");
        regEntity(EntityBullet.class, "EntityBullet");
    }

    public static void regRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerGhost.class, RenderPlayerGhost.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderBullet.FACTORY);
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.entity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    private static <T extends Entity> void regRender(Class<T> entityClass, IRenderFactory<? super T> renderFactory)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }

    public static void regEntities()
    {
        regEntity(EntityPlayerGhost.class, "PlayerGhost");
        regEntity(EntityBullet.class, "Bullet");
        regEntity(EntityFlare.class, "Flare");
        regEntity(EntityGrenade.class, "Grenade");
    }

    @SideOnly(Side.CLIENT)
    public static void regRenders()
    {
        regRender(EntityPlayerGhost.class, RenderPlayerGhost.FACTORY);
        regRender(EntityBullet.class, RenderBullet.FACTORY);
        regRender(EntityFlare.class, RenderFlare.FACTORY);
        regRender(EntityGrenade.class, new IRenderFactory<EntityGrenade>()
        {
            @Override
            public Render<? super EntityGrenade> createRenderFor(RenderManager manager)
            {
                return new RenderSnowball<EntityGrenade>(manager, Items.GUNPOWDER, Minecraft.getMinecraft().getRenderItem());
            }
        });
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.entity.EntityPlayerGhost;
import com.brightspark.bitsandbobs.entity.RenderPlayerGhost;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BABEntities
{
    private static int modEntityId = 0;

    public static void init(boolean isClientSide)
    {
        EntityRegistry.registerModEntity(EntityPlayerGhost.class, "PlayerGhost", ++modEntityId, BitsAndBobs.instance, 64, 1, false);

        if(isClientSide)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityPlayerGhost.class, RenderPlayerGhost.FACTORY);
        }
    }
}

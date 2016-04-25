package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.ItemLifeStick;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABItems
{
    public static final ItemLifeStick lifeStick = new ItemLifeStick();

    public static void init()
    {
        GameRegistry.registerItem(lifeStick, Names.Items.LIFE_STICK);
    }

    public static void regModels()
    {
        Common.regModel(lifeStick);
    }
}

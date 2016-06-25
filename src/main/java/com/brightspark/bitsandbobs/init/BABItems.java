package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.ItemBasic;
import com.brightspark.bitsandbobs.item.ItemDebug;
import com.brightspark.bitsandbobs.item.ItemLifeStick;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABItems
{
    public static final ItemLifeStick lifeStick = new ItemLifeStick();
    public static final ItemBasic itemBloodPrismarine = new ItemBasic(Names.Items.BLOOD_PRISMARINE);

    public static final ItemDebug itemDebug = new ItemDebug(Names.Items.DEBUG);

    public static void init()
    {
        GameRegistry.register(lifeStick);
        GameRegistry.register(itemBloodPrismarine);

        GameRegistry.register(itemDebug);
    }

    public static void regModels()
    {
        Common.regModel(lifeStick);
        Common.regModel(itemBloodPrismarine);

        Common.regModel(itemDebug);
    }
}

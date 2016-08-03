package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.*;
import com.brightspark.bitsandbobs.util.Common;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABItems
{
    public static final ItemLifeStick itemLifeStick = new ItemLifeStick();
    public static final ItemBasic itemBloodPrismarine = new ItemBasic(Names.Items.BLOOD_PRISMARINE);
    public static final ItemMirageOrb itemMirageOrb = new ItemMirageOrb();
    public static final ItemFlareGun itemFlareGun = new ItemFlareGun();
    public static final ItemBasic itemFlareAmmo = new ItemBasic(Names.Items.FLARE_AMMO);

    public static final ItemDebug itemDebug = new ItemDebug(Names.Items.DEBUG);

    public static void init()
    {
        GameRegistry.register(itemLifeStick);
        GameRegistry.register(itemBloodPrismarine);
        GameRegistry.register(itemMirageOrb);
        GameRegistry.register(itemFlareGun);
        GameRegistry.register(itemFlareAmmo);

        GameRegistry.register(itemDebug);
    }

    public static void regModels()
    {
        Common.regModel(itemLifeStick);
        Common.regModel(itemBloodPrismarine);
        Common.regModel(itemMirageOrb);
        Common.regModel(itemFlareGun);
        Common.regModel(itemFlareAmmo);

        Common.regModel(itemDebug);
    }
}

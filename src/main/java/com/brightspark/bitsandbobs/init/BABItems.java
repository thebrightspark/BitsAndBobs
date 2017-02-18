package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.*;
import com.brightspark.bitsandbobs.util.ClientUtils;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABItems
{
    public static List<Item> ITEMS = new ArrayList<Item>();

    public static ItemLifeStick itemLifeStick;
    public static ItemBasic itemBloodPrismarine;
    public static ItemMirageOrb itemMirageOrb;
    public static ItemFlareGun itemFlareGun;
    public static ItemBasic itemFlareAmmo;

    public static ItemBasic itemBullet;
    public static ItemBulletClip itemBulletClip;
    public static ItemAmmoBelt itemAmmoBelt;
    public static ItemGun itemGun;
    public static ItemMinigun itemMinigun;

    public static ItemDebug itemDebug;

    public static ItemStack itemBucketXpJuice;

    public static void regItem(Item item)
    {
        GameRegistry.register(item);
        ITEMS.add(item);
    }

    public static void regItems()
    {
        regItem(itemLifeStick = new ItemLifeStick());
        regItem(itemBloodPrismarine = new ItemBasic(Names.Items.BLOOD_PRISMARINE));
        regItem(itemMirageOrb = new ItemMirageOrb());
        regItem(itemFlareGun = new ItemFlareGun());
        regItem(itemFlareAmmo = new ItemBasic(Names.Items.FLARE_AMMO));

        regItem(itemBullet = new ItemBasic(Names.Items.BULLET));
        regItem(itemBulletClip = new ItemBulletClip(Names.Items.BULLET_CLIP));
        regItem(itemAmmoBelt = new ItemAmmoBelt());
        regItem(itemGun = new ItemGun(Names.Items.GUN));
        regItem(itemMinigun = new ItemMinigun(Names.Items.MINIGUN));

        regItem(itemDebug = new ItemDebug(Names.Items.DEBUG));

        /*
        if(FluidRegistry.isUniversalBucketEnabled())
        {
            if(BABFluids.fluidXpJuice != null)
            {
                FluidRegistry.addBucketForFluid(BABFluids.fluidXpJuice);
                itemBucketXpJuice = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BABFluids.fluidXpJuice);
            }
        }
        */
    }

    public static void regModels()
    {
        for(Item item : ITEMS)
            ClientUtils.regModel(item);
    }
}

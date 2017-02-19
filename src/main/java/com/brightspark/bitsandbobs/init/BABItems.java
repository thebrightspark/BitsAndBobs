package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.*;
import com.brightspark.bitsandbobs.util.ClientUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABItems
{
    public static List<Item> ITEMS = new ArrayList<Item>();

    public static ItemBasicMeta itemBasic;

    public static ItemLifeStick itemLifeStick;
    //public static ItemBasic itemBloodPrismarine;
    public static ItemMirageOrb itemMirageOrb;
    public static ItemFlareGun itemFlareGun;
    //public static ItemBasic itemFlareAmmo;

    //public static ItemBasic itemBullet;
    public static ItemBulletClip itemBulletClip;
    public static ItemAmmoBelt itemAmmoBelt;
    public static ItemPistol itemPistol;
    public static ItemBulletClip itemMinigunClip;
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
        regItem(itemBasic = new ItemBasicMeta("basic", EItemBasic.allNames));

        regItem(itemLifeStick = new ItemLifeStick());
        //regItem(itemBloodPrismarine = new ItemBasic("itemBloodPrismarine"));
        regItem(itemMirageOrb = new ItemMirageOrb());
        regItem(itemFlareGun = new ItemFlareGun());
        //regItem(itemFlareAmmo = new ItemBasic("itemFlareAmmo"));

        //regItem(itemBullet = new ItemBasic("itemBullet"));
        regItem(itemBulletClip = new ItemBulletClip("itemBulletClip", 10));
        regItem(itemAmmoBelt = new ItemAmmoBelt());
        regItem(itemPistol = new ItemPistol());
        regItem(itemMinigunClip = new ItemBulletClip("itemMinigunClip", 100));
        regItem(itemMinigun = new ItemMinigun("itemMinigun"));

        regItem(itemDebug = new ItemDebug("itemDebug"));

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

    public static ItemStack getBasicItem(EItemBasic basicName)
    {
        return getBasicItem(basicName, 1);
    }
    
    public static ItemStack getBasicItem(EItemBasic basicName, int stackSize)
    {
        return new ItemStack(itemBasic, stackSize, basicName.ordinal());
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.*;
import com.brightspark.bitsandbobs.item.gun.*;
import com.brightspark.bitsandbobs.util.ClientUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BABItems
{
    public static List<Item> ITEMS = new ArrayList<Item>();

    public static Item itemBasic, itemLifeStick, itemMirageOrb, itemFlareGun, itemEntityNoDespawn,
            itemAmmoBelt, itemPistol, itemMinigun, itemShotgun, itemGrenade,
            itemDebug, itemBucketXpJuice;

    public static ItemBulletClip itemPistolClip, itemMinigunClip;
    public static ItemBullet itemFlareAmmo, itemShotgunShell;

    public static void regItem(Item item)
    {
        GameRegistry.register(item);
        ITEMS.add(item);
    }

    public static void regItems()
    {
        regItem(itemBasic = new ItemBasicMeta("basic", EItemBasic.allNames));

        regItem(itemLifeStick = new ItemLifeStick());
        regItem(itemMirageOrb = new ItemMirageOrb());
        regItem(itemEntityNoDespawn = new ItemEntityNoDespawn());

        regItem(itemAmmoBelt = new ItemAmmoBelt());

        regItem(itemFlareAmmo = new ItemBullet("flareAmmo", 1));
        regItem(itemFlareGun = new ItemFlareGun());
        regItem(itemPistolClip = new ItemBulletClip("pistolClip", 10));
        regItem(itemPistol = new ItemPistol());
        regItem(itemMinigunClip = new ItemBulletClip("minigunClip", 100));
        regItem(itemMinigun = new ItemMinigun());
        regItem(itemShotgunShell = new ItemBullet("shotgunShell", 16));
        regItem(itemShotgun = new ItemShotgun());
        regItem(itemGrenade = new ItemGrenade());

        regItem(itemDebug = new ItemDebug());

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

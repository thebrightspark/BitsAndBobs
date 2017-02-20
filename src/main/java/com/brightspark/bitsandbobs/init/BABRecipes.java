package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.util.ShapelessNBTRecipe;
import com.brightspark.bitsandbobs.item.ItemBulletClip;
import com.brightspark.bitsandbobs.util.ShapedNBTRecipe;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Arrays;
import java.util.Map;

public class BABRecipes
{
    public static void init()
    {
        //Register Recipe
        RecipeSorter.register("ShapelessNBTRecipe", ShapelessNBTRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        //Life Stick
        GameRegistry.addRecipe(new ItemStack(BABItems.itemLifeStick), " zx", "zyz", "xz ", 'x', Items.BLAZE_ROD, 'y', Items.NETHER_STAR, 'z', Items.GLOWSTONE_DUST);
        //Healing Block
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing), "qsq", "sds", "qsq", 'q', Blocks.QUARTZ_BLOCK, 's', BABItems.itemLifeStick, 'd', Blocks.DRAGON_EGG);
        //Stellar Egg
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockStellarEgg), "sss", "sds", "sss", 's', Items.NETHER_STAR, 'd', Blocks.DRAGON_EGG);
        //Fast Healing Block
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing2), "php", "hdh", "php", 'h', BABBlocks.blockHealing, 'p', BABItems.getBasicItem(EItemBasic.BLOOD_PRISMARINE), 'd', BABBlocks.blockStellarEgg);
        //Trash Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BABBlocks.blockTrash), "shs", "s s", "sls", 's', "stone", 'h', Blocks.HOPPER, 'l', Items.LAVA_BUCKET));
        //Mirage Orb
        GameRegistry.addRecipe(new ItemStack(BABItems.itemMirageOrb), " p ", "pep", " p ", 'e', Items.ENDER_EYE, 'p', Items.PRISMARINE_CRYSTALS);
        //Flare Ammo
        GameRegistry.addShapelessRecipe(BABItems.getBasicItem(EItemBasic.FLARE_AMMO), Items.IRON_INGOT, Items.GUNPOWDER, new ItemStack(Items.DYE, 1, 1));
        //Flare Gun
        GameRegistry.addRecipe(new ItemStack(BABItems.itemFlareGun), "iib", " oi", 'i', Items.IRON_INGOT, 'o', new ItemStack(Items.DYE, 1, 14), 'b', Blocks.STONE_BUTTON);

        //Bullet
        GameRegistry.addShapelessRecipe(BABItems.getBasicItem(EItemBasic.FLARE_AMMO, 4), Items.IRON_INGOT, Items.GUNPOWDER);
        //Empty Bullet Clip
        ItemStack emptyClip = new ItemStack(BABItems.itemBulletClip);
        ItemBulletClip.setBulletsAmount(emptyClip, 0);
        GameRegistry.addRecipe(emptyClip, "i i", "i i", " i ", 'i', Items.IRON_INGOT);
        //Bullet Clips
        int max = BABItems.itemBulletClip.clipSize;
        for(int clipSize = 0; clipSize < max; clipSize++)
        {
            for(int numBullets = 1; numBullets <= Math.min(Math.min(max, 8), max - clipSize); numBullets++)
            {
                //Add recipes to fill a bullet clip for every possible combination
                ItemStack[] bullets = new ItemStack[numBullets + 1];
                Arrays.fill(bullets, BABItems.getBasicItem(EItemBasic.BULLET));

                ItemStack inputClip = new ItemStack(BABItems.itemBulletClip);
                ItemBulletClip.setBulletsAmount(inputClip, clipSize);
                bullets[0] = inputClip;

                ItemStack outputClip = new ItemStack(BABItems.itemBulletClip);
                ItemBulletClip.setBulletsAmount(outputClip, clipSize + numBullets);

                GameRegistry.addRecipe(new ShapelessNBTRecipe(outputClip, (Object[]) bullets));
            }
        }

        //Pistol
        GameRegistry.addRecipe(BABItems.getBasicItem(EItemBasic.GUN_BARREL), "iii", "iii", 'i', Items.IRON_INGOT);
        GameRegistry.addRecipe(BABItems.getBasicItem(EItemBasic.PISTOL_ASSEMBLY), "di", "ii", "i ", 'i', Items.IRON_INGOT, 'd', Items.DIAMOND);
        GameRegistry.addRecipe(new ItemStack(BABItems.itemPistol), "ab", 'b', BABItems.getBasicItem(EItemBasic.GUN_BARREL), 'a', BABItems.getBasicItem(EItemBasic.PISTOL_ASSEMBLY));
        //Minigun
        GameRegistry.addRecipe(BABItems.getBasicItem(EItemBasic.MINIGUN_BARREL), "ii", "ii", 'i', BABItems.getBasicItem(EItemBasic.GUN_BARREL));
        GameRegistry.addRecipe(BABItems.getBasicItem(EItemBasic.MINIGUN_ASSEMBLY), "iii", "dni", "ii ", 'i', Items.IRON_INGOT, 'd', Items.DIAMOND, 'n', Items.NETHER_STAR);
        GameRegistry.addRecipe(new ItemStack(BABItems.itemMinigun), "ab", 'b', BABItems.getBasicItem(EItemBasic.MINIGUN_BARREL), 'a', BABItems.getBasicItem(EItemBasic.MINIGUN_ASSEMBLY));
    }

    /**
     * Adds a new ShapedNBTRecipe
     */
    private static void addShapedNBTRecipe(ItemStack outputStack, Object... inputList)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (inputList[i] instanceof String[])
        {
            String[] astring = (String[])inputList[i++];

            for (String s2 : astring)
            {
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        else
            while (inputList[i] instanceof String)
            {
                String s1 = (String)inputList[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }

        Map<Character, ItemStack> map;

        for (map = Maps.newHashMap(); i < inputList.length; i += 2)
        {
            Character character = (Character)inputList[i];
            ItemStack itemstack = null;

            if (inputList[i + 1] instanceof Item)
                itemstack = new ItemStack((Item)inputList[i + 1]);
            else if (inputList[i + 1] instanceof Block)
                itemstack = new ItemStack((Block)inputList[i + 1], 1, 32767);
            else if (inputList[i + 1] instanceof ItemStack)
                itemstack = (ItemStack)inputList[i + 1];

            map.put(character, itemstack);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int l = 0; l < j * k; ++l)
        {
            char c0 = s.charAt(l);

            if(map.containsKey(c0))
                aitemstack[l] = (map.get(c0)).copy();
            else
                aitemstack[l] = null;
        }

        GameRegistry.addRecipe(new ShapedNBTRecipe(j, k, aitemstack, outputStack));
    }
}

package com.brightspark.bitsandbobs.init;

import com.brightspark.bitsandbobs.item.ItemBulletClip;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Arrays;

public class BABRecipes
{
    public static void init()
    {
        //Life Stick
        GameRegistry.addRecipe(new ItemStack(BABItems.itemLifeStick), " zx", "zyz", "xz ", 'x', Items.BLAZE_ROD, 'y', Items.NETHER_STAR, 'z', Items.GLOWSTONE_DUST);
        //Healing Block
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing), "qsq", "sds", "qsq", 'q', Blocks.QUARTZ_BLOCK, 's', BABItems.itemLifeStick, 'd', Blocks.DRAGON_EGG);
        //Stellar Egg
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockStellarEgg), "sss", "sds", "sss", 's', Items.NETHER_STAR, 'd', Blocks.DRAGON_EGG);
        //Fast Healing Block
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing2), "php", "hdh", "php", 'h', BABBlocks.blockHealing, 'p', BABItems.itemBloodPrismarine, 'd', BABBlocks.blockStellarEgg);
        //Trash Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BABBlocks.blockTrash), "shs", "s s", "sls", 's', "stone", 'h', Blocks.HOPPER, 'l', Items.LAVA_BUCKET));
        //Mirage Orb
        GameRegistry.addRecipe(new ItemStack(BABItems.itemMirageOrb), " p ", "pep", " p ", 'e', Items.ENDER_EYE, 'p', Items.PRISMARINE_CRYSTALS);
        //Flare Ammo
        GameRegistry.addShapelessRecipe(new ItemStack(BABItems.itemFlareAmmo), Items.IRON_INGOT, Items.GUNPOWDER, new ItemStack(Items.DYE, 1, 1));
        //Flare Gun
        GameRegistry.addRecipe(new ItemStack(BABItems.itemFlareGun), "iib", " oi", 'i', Items.IRON_INGOT, 'o', new ItemStack(Items.DYE, 1, 14), 'b', Blocks.STONE_BUTTON);

        //Bullet
        GameRegistry.addShapelessRecipe(new ItemStack(BABItems.itemBullet, 4), Items.IRON_INGOT, Items.GUNPOWDER);
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
                Item[] bullets = new Item[numBullets];
                Arrays.fill(bullets, BABItems.itemBullet);
                ItemStack outputClip = new ItemStack(BABItems.itemBulletClip);
                ItemBulletClip.setBulletsAmount(outputClip, clipSize + numBullets);
                GameRegistry.addShapelessRecipe(outputClip, bullets);
            }
        }
        //TODO: Gun
    }
}

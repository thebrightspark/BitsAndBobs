package com.brightspark.bitsandbobs.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BABRecipes
{
    public static void init()
    {
        GameRegistry.addRecipe(new ItemStack(BABItems.itemLifeStick), " zx", "zyz", "xz ", 'x', Items.BLAZE_ROD, 'y', Items.NETHER_STAR, 'z', Items.GLOWSTONE_DUST);
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing), "qsq", "sds", "qsq", 'q', Blocks.QUARTZ_BLOCK, 's', BABItems.itemLifeStick, 'd', Blocks.DRAGON_EGG);
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockStellarEgg), "sss", "sds", "sss", 's', Items.NETHER_STAR, 'd', Blocks.DRAGON_EGG);
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing2), "php", "hdh", "php", 'h', BABBlocks.blockHealing, 'p', BABItems.itemBloodPrismarine, 'd', BABBlocks.blockStellarEgg);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BABBlocks.blockTrash), "shs", "s s", "sls", 's', "stone", 'h', Blocks.HOPPER, 'l', Items.LAVA_BUCKET));
        GameRegistry.addRecipe(new ItemStack(BABItems.itemMirageOrb), " p ", "pep", " p ", 'e', Items.ENDER_EYE, 'p', Items.PRISMARINE_CRYSTALS);
        GameRegistry.addShapelessRecipe(new ItemStack(BABItems.itemFlareAmmo), Items.IRON_INGOT, Items.GUNPOWDER, new ItemStack(Items.DYE, 1, 1));
        GameRegistry.addRecipe(new ItemStack(BABItems.itemFlareGun), "iib", " oi", 'i', Items.IRON_INGOT, 'o', new ItemStack(Items.DYE, 1, 14), 'b', Blocks.STONE_BUTTON);
    }
}

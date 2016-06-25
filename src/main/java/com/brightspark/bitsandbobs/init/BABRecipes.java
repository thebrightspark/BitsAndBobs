package com.brightspark.bitsandbobs.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABRecipes
{
    @SuppressWarnings("all")
    public static void init()
    {
        //Life Stick
        GameRegistry.addRecipe(new ItemStack(BABItems.lifeStick), new Object[] {" zx", "zyz", "xz ", 'x', Items.BLAZE_ROD, 'y', Items.NETHER_STAR, 'z', Items.GLOWSTONE_DUST});
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing), new Object[] {"qsq", "sds", "qsq", 'q', Blocks.QUARTZ_BLOCK, 's', BABItems.lifeStick, 'd', Blocks.DRAGON_EGG});
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockStellarEgg), new Object[] {"sss", "sds", "sss", 's', Items.NETHER_STAR, 'd', Blocks.DRAGON_EGG});
        GameRegistry.addRecipe(new ItemStack(BABBlocks.blockHealing2), new Object[] {"php", "hdh", "php", 'h', BABBlocks.blockHealing2, 'p', BABItems.itemBloodPrismarine, 'd', BABBlocks.blockStellarEgg});
    }
}

package com.brightspark.bitsandbobs.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BABRecipes
{
    @SuppressWarnings("all")
    public static void init()
    {
        //Life Stick
        GameRegistry.addRecipe(new ItemStack(BABItems.lifeStick), new Object[] {" zx", "zyz", "xz ", 'x', Items.blaze_rod, 'y', Items.nether_star, 'z', Items.glowstone_dust});
    }
}

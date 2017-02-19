package com.brightspark.bitsandbobs.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

public class ShapedNBTRecipe extends ShapedRecipes
{
    public ShapedNBTRecipe(int width, int height, ItemStack[] inputList, ItemStack output)
    {
        super(width, height, inputList, output);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        for(int i = 0; i <= 3 - this.recipeWidth; ++i)
            for(int j = 0; j <= 3 - this.recipeHeight; ++j)
            {
                if(checkMatch(inv, i, j, true))
                    return true;

                if(checkMatch(inv, i, j, false))
                    return true;
            }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    public boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for(int i = 0; i < 3; ++i)
            for(int j = 0; j < 3; ++j)
            {
                int k = i - p_77573_2_;
                int l = j - p_77573_3_;
                ItemStack itemstack = null;

                if(k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
                {
                    if(p_77573_4_)
                        itemstack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
                    else
                        itemstack = this.recipeItems[k + l * this.recipeWidth];
                }

                ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(i, j);

                if(!ItemStack.areItemStacksEqual(itemstack, itemstack1))
                    return false;
            }
        return true;
    }
}

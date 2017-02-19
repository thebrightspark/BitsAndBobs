package com.brightspark.bitsandbobs;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.List;

public class ShapelessNBTRecipe extends ShapelessRecipes
{
    public ShapelessNBTRecipe(ItemStack output, Object... inputList)
    {
        super(output, parseInputList(inputList));
    }

    private static List<ItemStack> parseInputList(Object[] inputList)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for(Object object : inputList)
        {
            if(object instanceof ItemStack)
                list.add(((ItemStack)object).copy());
            else if(object instanceof Item)
                list.add(new ItemStack((Item)object));
            else
            {
                if(!(object instanceof Block))
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                list.add(new ItemStack((Block)object));
            }
        }

        return list;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        List<ItemStack> list = Lists.newArrayList(this.recipeItems);

        for(int i = 0; i < inv.getHeight(); ++i)
            for(int j = 0; j < inv.getWidth(); ++j)
            {
                ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

                if(itemstack != null)
                {
                    boolean flag = false;

                    for(ItemStack itemstack1 : list)
                        if(ItemStack.areItemStacksEqual(itemstack, itemstack1))
                        {
                            flag = true;
                            list.remove(itemstack1);
                            break;
                        }

                    if(!flag)
                        return false;
                }
            }

        return list.isEmpty();
    }
}

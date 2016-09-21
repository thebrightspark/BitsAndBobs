package com.brightspark.bitsandbobs.reference;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class Config
{
    public static String[] healingBlockValidFuel = new String[]{"minecraft:golden_apple,0,2", "minecraft:golden_apple,1,18", "minecraft:log,2,1"};
    /** This is populated in the mod's init() from the array above */
    public static HashMap<ItemStack, Integer> healingBlockValidFuelStacks = new HashMap<ItemStack, Integer>();

    /** The mirage orb's cooldown in seconds */
    public static int mirageOrbCooldown = 60;
    /** The mirage orb ghost's life in seconds */
    public static int mirageOrbGhostLife = 10;
}

package com.brightspark.bitsandbobs.reference;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class Config
{
    public static String[] healingBlockValidFuel = new String[]{"minecraft:golden_apple,0,2", "minecraft:golden_apple,1,18"};
    /** This is populated in the mod's init() from the array above */
    public static HashMap<ItemStack, Integer> healingBlockValidFuelStacks = new HashMap<ItemStack, Integer>();

    /** The mirage orb's cooldown in seconds */
    public static int mirageOrbCooldown = 60;
    /** The mirage orb ghost's life in seconds */
    public static int mirageOrbGhostLife = 10;

    /** Name for the kill command - configurable in-case of conflicting command names */
    public static String commandKillName = "slay";
    public static String[] commandKillMessages = new String[] {
            "Poof... %s entities vanished into the void.",
            "Poof, and the entities are gone. (%s)",
            "Once upon a time, %s entities went poof.\nThe end.",
            "*Big distant explosion* (%s)",
            "Whoops, my finger slipped! (%s)",
            "Bye bye. Miss you already. (%s)",
            "Adios amigos. (%s)",
            "Killed %s entities.",
            "Murdered %s entities.",
            "Slain %s entities.",
            "Butchered %s entities.",
            "Who did that!? (%s)",
            "What does this button do? (%s)"
    };

    /** The radius for the area of effect for the interdiction torches */
    public static int interdictionTorchRadius = 4;

    /** The strength for the interdiction torches */
    public static float interdictionTorchStrength = 0.5F;
}

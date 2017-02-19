package com.brightspark.bitsandbobs.init;

public enum EItemBasic
{
    BLOOD_PRISMARINE("bloodPrismarine"),
    FLARE_AMMO("flareAmmo"),
    BULLET("bullet"),
    GUN_BARREL("gunBarrel"),
    PISTOL_ASSEMBLY("pistolAssembly"),
    MINIGUN_BARREL("minigunBarrel"),
    MINIGUN_ASSEMBLY("minigunAssembly");

    public String name;
    public static final String[] allNames;

    static
    {
        allNames = new String[EItemBasic.values().length];
        for(EItemBasic e : EItemBasic.values())
            allNames[e.ordinal()] = e.name;
    }

    EItemBasic(String name)
    {
        this.name = name;
    }
}

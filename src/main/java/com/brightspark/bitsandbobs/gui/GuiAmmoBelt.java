package com.brightspark.bitsandbobs.gui;

import net.minecraft.entity.player.EntityPlayer;

public class GuiAmmoBelt extends BABGuiContainer
{
    public GuiAmmoBelt(EntityPlayer player)
    {
        super(new ContainerAmmoBelt(player), "guiAmmoBelt", "Ammo Belt");
        textPlayerInvY = 38;
    }
}

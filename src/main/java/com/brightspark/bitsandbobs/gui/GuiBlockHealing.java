package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.init.BABBlocks;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlockHealing extends BABGuiContainer
{
    private TileHealing te;

    public GuiBlockHealing(InventoryPlayer invPlayer, TileHealing te)
    {
        super(new ContainerBlockHealing(invPlayer, te), "guiBlockHealing.png", BABBlocks.blockHealing.getUnlocalizedName() + ".name");
        this.xSize = 184;
        this.ySize = 219;
        textPlayerInvY = 73;
        this.te = te;
    }

    @Override
    protected void drawText()
    {
        super.drawText();
        fontRenderer.drawString("Fuel:", 77, 50, 0);
        String fuelString = Integer.toString(te.getFuelAmount());
        fontRenderer.drawString(fuelString, 88 - (fontRenderer.getStringWidth(fuelString) / 2), 61, 0);
    }
}

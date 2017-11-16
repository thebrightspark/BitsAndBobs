package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.init.BABBlocks;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlockTrash extends BABGuiContainer
{
    public GuiBlockTrash(InventoryPlayer invPlayer, TileTrash te)
    {
        super(new ContainerBlockTrash(invPlayer, te), "guiBlockTrash", BABBlocks.blockTrash.getUnlocalizedName() + ".name");
        this.xSize = 175;
        this.ySize = 162;
        textPlayerInvY = 70;
    }
}

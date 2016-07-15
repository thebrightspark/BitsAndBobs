package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.container.ContainerBlockHealing;
import com.brightspark.bitsandbobs.container.ContainerBlockTrash;
import com.brightspark.bitsandbobs.init.BABBlocks;
import com.brightspark.bitsandbobs.reference.Reference;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Mark on 28/06/2016.
 */
public class GuiBlockTrash extends GuiContainer
{
    public static final ResourceLocation guiImage = new ResourceLocation(Reference.GUI_TEXTURE_DIR + "guiBlockTrash.png");

    public GuiBlockTrash(InventoryPlayer invPlayer, World world, int x, int y, int z)
    {
        super(new ContainerBlockTrash(invPlayer, (TileTrash) world.getTileEntity(new BlockPos(x, y, z))));
        this.xSize = 175;
        this.ySize = 162;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //Draw gui
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiImage);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        //TODO: Finish GuiBlockTrash
        fontRendererObj.drawString(I18n.format(BABBlocks.blockTrash.getUnlocalizedName() + ".name"), 8, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, 73, 4210752);
    }
}

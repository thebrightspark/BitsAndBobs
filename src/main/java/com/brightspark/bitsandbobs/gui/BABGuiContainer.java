package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class BABGuiContainer extends GuiContainer
{
    private ResourceLocation guiImage;
    protected String inventoryName;
    protected int textColour = 4210752;
    protected int textPlayerInvY = 82;

    public BABGuiContainer(Container container, String guiImageName, String inventoryName)
    {
        super(container);
        guiImage = new ResourceLocation(Reference.MOD_ID, Reference.GUI_TEXTURE_DIR + guiImageName + ".png");
        this.inventoryName = inventoryName;
        xSize = 176;
        ySize = 168;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //Draw gui
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(guiImage);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        drawText();

        mouseX -= guiLeft;
        mouseY -= guiTop;
        List<String> tooltip = new ArrayList<String>();
        drawTooltips(tooltip, mouseX, mouseY);
        if(!tooltip.isEmpty())
            drawHoveringText(tooltip, mouseX, mouseY);
    }

    protected void drawText()
    {
        fontRendererObj.drawString(inventoryName, 8, 6, textColour);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, textPlayerInvY, textColour);
    }

    protected void drawTooltips(List<String> tooltip, int mouseX, int mouseY) {}
}

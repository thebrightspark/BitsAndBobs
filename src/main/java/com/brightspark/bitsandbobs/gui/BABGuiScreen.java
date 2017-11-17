package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BABGuiScreen extends GuiScreen
{
    protected static ResourceLocation guiImage;
    protected int xSize = 176;
    protected int ySize = 168;
    protected int guiLeft, guiTop;

    public BABGuiScreen(String guiImageName)
    {
        guiImage = guiImageName == null ? null : new ResourceLocation(Reference.MOD_ID, Reference.GUI_TEXTURE_DIR + guiImageName + ".png");
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        //Draw GUI background
        if(guiImage != null)
        {
            mc.getTextureManager().bindTexture(guiImage);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        }
        drawExtraBg();

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawText();

        List<String> tooltip = new ArrayList<String>();
        drawTooltips(tooltip, mouseX, mouseY);
        if(!tooltip.isEmpty())
            drawHoveringText(tooltip, mouseX, mouseY);
    }

    protected void drawExtraBg() {}

    protected void drawText() {}

    protected void drawTooltips(List<String> tooltip, int mouseX, int mouseY) {}

    public void drawString(String text, int x, int y, int color, boolean drawShadow)
    {
        fontRenderer.drawString(text, (float)x, (float)y, color, drawShadow);
    }

    public void drawString(String text, int x, int y, int color)
    {
        drawString(text, x, y, color, false);
    }

    public void drawStringWithShadow(String text, int x, int y, int color)
    {
        drawString(text, x, y, color, true);
    }

    public void drawCenteredString(String text, int x, int y, int color, boolean drawShadow)
    {
        fontRenderer.drawString(text, (float)(x - fontRenderer.getStringWidth(text) / 2), (float)y, color, drawShadow);
    }

    public void drawCenteredString(String text, int x, int y, int color)
    {
        drawCenteredString(text, x, y, color, false);
    }

    public void drawCenteredStringWithShadow(String text, int x, int y, int color)
    {
        drawCenteredString(text, x, y, color, true);
    }

    protected class BABButton extends GuiButton
    {
        protected final int iconX, iconY;

        public BABButton(int x, int y, int widthIn, int heightIn, int iconXIn, int iconYIn, String buttonText)
        {
            super(buttonList.size(), guiLeft + x, guiTop + y, widthIn, heightIn, buttonText);
            iconX = iconXIn;
            iconY = iconYIn;
        }

        protected int getTextColour()
        {
            return enabled ? 14737632 : 10526880;
        }

        public String getTooltipText()
        {
            return null;
        }

        //Overriding this to remove the shadow
        public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y)
        {
            drawCenteredString(fontRendererIn, text, x, y, getTextColour());
        }

        @Override
        public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int colour)
        {
            fontRendererIn.drawString(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, colour, false);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if(!visible) return;
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(guiImage);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            //Draw button
            drawTexturedModalRect(x, y, iconX, iconY, width, height);
            if(!displayString.equals(""))
                drawCenteredString(fontrenderer, displayString, x + width / 2, y + (height - 8) / 2);
        }
    }
}

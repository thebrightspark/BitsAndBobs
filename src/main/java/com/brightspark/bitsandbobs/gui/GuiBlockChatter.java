package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.message.MessageUpdateChatter;
import com.brightspark.bitsandbobs.tileentity.TileChatter;
import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class GuiBlockChatter extends BABGuiScreen
{
    private GuiTextField textField;
    private TileChatter te;

    public GuiBlockChatter(TileChatter te)
    {
        super(null);
        //xSize = 175;
        //ySize = 162;
        this.te = te;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        textField = new GuiTextField(0, fontRendererObj, width / 2 - 150, 50, 300, 20);
        textField.setFocused(true);
        textField.setCanLoseFocus(false);
        textField.setMaxStringLength(100);
        textField.setText(te.getMessage());
        addButton(new GuiButton(0, width / 2 - 150, 80, 300, 20, "Done"));

        int xStart = width / 2 - 40;
        int yStart = 110;

        byte colourI = 0;
        for(int y = 0; y < 4; y++)
            for(int x = 0; x < 4; x++)
            {
                String colour = TextFormatting.fromColorIndex(colourI++).toString();
                addButton(new ColourButton(xStart + x * 20, yStart + y * 20, colour, mc.fontRendererObj.getColorCode(colour.charAt(1))));
            }
    }

    @Override
    protected void drawText()
    {
        super.drawText();
        textField.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if(keyCode == 1)
        {
            mc.player.closeScreen();
            return;
        }
        textField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Writes the colour code to the text field.
     * This is basically a copy of GuiTextField#writeText just without the character filter.
     */
    private void writeColour(ColourButton button)
    {
        String textToWrite = button.getColourString();
        String text = textField.getText();
        int cursorPos = textField.getCursorPosition();
        int selectionEnd = textField.getSelectionEnd();

        String s = "";
        int selectStart = cursorPos < selectionEnd ? cursorPos : selectionEnd;
        int selectEnd = cursorPos < selectionEnd ? selectionEnd : cursorPos;
        int maxWriteLength = textField.getMaxStringLength() - text.length() - (selectStart - selectEnd);

        if(!text.isEmpty())
            s = s + text.substring(0, selectStart);

        int l;

        if(maxWriteLength < textToWrite.length())
        {
            s = s + textToWrite.substring(0, maxWriteLength);
            l = maxWriteLength;
        }
        else
        {
            s = s + textToWrite;
            l = textToWrite.length();
        }

        if(!text.isEmpty() && selectEnd < text.length())
            s = s + text.substring(selectEnd);

        textField.setText(s);
        textField.moveCursorBy(selectStart - selectionEnd + l);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 0)
            mc.player.closeScreen();
        else if(button instanceof ColourButton)
            writeColour((ColourButton) button);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        te.setMessage(textField.getText());
        BitsAndBobs.NETWORK.sendToServer(new MessageUpdateChatter(te.getPos(), te.getMessage()));
    }

    private class ColourButton extends GuiButton
    {
        private static final int greyMargin = 2;
        private String colourString;
        private int colourCode;

        public ColourButton(int x, int y, String colourString, int colourCode)
        {
            super(buttonList.size(), x, y, 16, 16, "");
            this.colourString = colourString;
            this.colourCode = colourCode;
        }

        public String getColourString()
        {
            //LogHelper.info(colourString);
            return colourString;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if(!visible) return;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            drawRect(xPosition, yPosition, xPosition + width, yPosition + height, 0xFF303030);
            drawRect(xPosition + greyMargin, yPosition + greyMargin, xPosition + width - greyMargin, yPosition + height - greyMargin, 0xFF000000 + colourCode);
        }
    }
}

package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.handler.ConfigHandler;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Mark on 13/02/2017.
 */
public class BABGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return BABGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        return null;
    }

    public static class BABGuiConfig extends GuiConfig
    {
        public BABGuiConfig(GuiScreen parentScreen)
        {
            super(parentScreen, getConfigElements(), Reference.MOD_ID, false, false, I18n.format(Reference.MOD_ID + ".config.title"));
        }

        private static List<IConfigElement> getConfigElements()
        {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            list.addAll((new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.Categories.GENERAL))).getChildElements());
            //list.add(new DummyConfigElement.DummyCategoryElement("babGeneral", Reference.MOD_ID + ".config.titleGeneral", (new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.Categories.GENERAL))).getChildElements(), GuiConfigEntries.CategoryEntry.class));
            return list;
        }
    }
}

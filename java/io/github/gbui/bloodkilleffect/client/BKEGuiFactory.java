package io.github.gbui.bloodkilleffect.client;

import io.github.gbui.bloodkilleffect.BloodKillEffectMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BKEGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return BKEConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class BKEConfigGui extends GuiConfig {
        public BKEConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), BloodKillEffectMod.MODID, false, false, I18n.format("bloodkilleffect.configgui.title"));
        }

        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            ConfigCategory category = BloodKillEffectMod.getConfig().getCategory(Configuration.CATEGORY_GENERAL);
            IConfigElement configElement = new ConfigElement(category);
            list.addAll(configElement.getChildElements());
            return list;
        }
    }
}

package com.chamoisest.miningmadness.client.screens;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screens.base.BaseScreen;
import com.chamoisest.miningmadness.common.containers.BasicQuarryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicQuarryScreen extends BaseScreen<BasicQuarryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MiningMadness.MODID,"textures/gui/basic_quarry_gui.png");

    public BasicQuarryScreen(BasicQuarryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

    }

    @Override
    protected void addWidgets() {
        int guiX = (width - imageWidth) / 2;
        int guiY = (height - imageHeight) / 2;

        addEnergyWidget(guiX + 153, guiY + 11);
        addUpgradeBars(guiX + 124, guiY + 17);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTexture(pGuiGraphics, TEXTURE);
    }
}

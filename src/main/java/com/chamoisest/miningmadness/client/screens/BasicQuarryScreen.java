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
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
        renderUpgradeBars(pGuiGraphics, 124, 17, pMouseX, pMouseY);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTexture(pGuiGraphics, TEXTURE);
    }
}

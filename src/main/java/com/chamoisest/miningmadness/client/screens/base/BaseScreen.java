package com.chamoisest.miningmadness.client.screens.base;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screens.elements.UpgradeBar;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapabilityProvider;
import com.chamoisest.miningmadness.common.containers.base.BaseMenu;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseMenu<?>> extends AbstractContainerScreen<T> {
    protected T menu;

    public BaseScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.menu = pMenu;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected abstract void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY);

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    protected void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation texture){
        int leftPos = this.leftPos;
        int topPos = this.topPos;

        pGuiGraphics.blit(texture, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    protected void renderUpgradeBars(GuiGraphics pGuiGraphics, int startX, int startY, int mouseX, int mouseY){
        this.menu.blockEntity.getCapability(MiningMadness.INFUSION_CAPABILITY).ifPresent(handler -> {
            int guiX = (width - imageWidth) / 2;
            int guiY = (height - imageHeight) / 2;

            List<MachineInfusionEnum> activeInfusions = handler.getActiveInfusions();
            int iterator = 0;
            for(MachineInfusionEnum infusion : activeInfusions){
                int currentValue = handler.getCurrentValueByMax(infusion);
                int maxValue = handler.getInfusion(infusion);

                new UpgradeBar(startX, startY + 6 * iterator , currentValue, maxValue, infusion).render(pGuiGraphics, mouseX, mouseY, 0);

                if(isMouseAboveArea(mouseX, mouseY, guiX, guiY, startX, startY + 6 * iterator, 23, 3)){
                    List<Component> text = List.of(infusion.getTooltipPrefix(), Component.literal(currentValue + "/" + maxValue));
                    pGuiGraphics.renderComponentTooltip(this.font, text, mouseX - guiX, mouseY - guiY);
                }

                iterator++;
            }
        });
    }
}

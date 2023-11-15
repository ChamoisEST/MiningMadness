package com.chamoisest.miningmadness.client.screens.base;

import com.chamoisest.miningmadness.client.screens.elements.EnergyInfoArea;
import com.chamoisest.miningmadness.client.screens.elements.UpgradeBar;
import com.chamoisest.miningmadness.common.blockentities.base.BaseEnergyEntity;
import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.common.containers.base.BaseMenu;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.EnumMap;
import java.util.Map;

public abstract class BaseScreen<T extends BaseMenu<?>> extends AbstractContainerScreen<T> {

    public BaseScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected abstract void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY);

    protected abstract void addWidgets();

    @Override
    protected void init() {
        super.init();
        addWidgets();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    protected void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation texture){
        pGuiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected void addUpgradeBars(int startX, int startY) {
        if(this.getMenu().blockEntity instanceof BaseInfusionEntity infusionEntity) {
            InfusionCapability cap = infusionEntity.getInfusionCapability();
            EnumMap<MachineInfusionEnum, Integer> activeInfusions = cap.getActiveInfusionMap();
            int iterator = 0;
            for (Map.Entry<MachineInfusionEnum, Integer> entry : activeInfusions.entrySet()) {
                if(cap.isCurrentValue(entry.getKey())) continue;

                int currentValue = cap.getCurrentValueByMaxType(entry.getKey());
                int maxValue = entry.getValue();

                this.addRenderableWidget(new UpgradeBar(this.font, startX, startY + 6 * iterator, currentValue, maxValue, entry.getKey()));

                iterator++;
            }
        }
    }

    protected void addEnergyWidget(int startX, int startY){
        if(this.getMenu().blockEntity instanceof BaseEnergyEntity) {
            this.addRenderableWidget(new EnergyInfoArea(
                    this.font,
                    startX,
                    startY,
                    (BaseEnergyEntity) this.getMenu().blockEntity));
        }
    }
}

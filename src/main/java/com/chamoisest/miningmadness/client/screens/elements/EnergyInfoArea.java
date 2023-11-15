package com.chamoisest.miningmadness.client.screens.elements;

import com.chamoisest.miningmadness.common.blockentities.base.BaseEnergyEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnergyInfoArea extends AbstractWidget {

    public final Rect2i area;

    private final BaseEnergyEntity entity;

    private final Font font;

    public EnergyInfoArea(Font font, int pX, int pY, BaseEnergyEntity entity){
        this(font, pX, pY, 14, 64, entity);
    }

    public EnergyInfoArea(Font font, int pX, int pY, int pWidth, int pHeight, BaseEnergyEntity entity) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.entity = entity;
        this.area = new Rect2i(pX, pY, pWidth, pHeight);
        this.font = font;
    }

    public Component getWidgetTooltip() {
        int energy = entity.getEnergyHandler().getEnergyStored();
        int maxEnergy = entity.getEnergyHandler().getMaxEnergyStored();

        return Component.literal(energy+"/"+maxEnergy+" FE");
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        final int width = area.getWidth();
        final int height = area.getHeight();

        int energy = entity.getEnergyHandler().getEnergyStored();
        int maxEnergy = entity.getEnergyHandler().getMaxEnergyStored();

        int stored = (int)(height*(energy/(float)maxEnergy));

        pGuiGraphics.fillGradient(
                area.getX(),
                area.getY()+(height-stored),
                area.getX() + width,
                area.getY() + height,
                0xffb51500,
                0xff600b00
        );

        if(this.isHovered()){
            pGuiGraphics.renderTooltip(font, getWidgetTooltip(), pMouseX, pMouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}

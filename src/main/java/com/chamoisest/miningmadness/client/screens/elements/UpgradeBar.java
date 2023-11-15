package com.chamoisest.miningmadness.client.screens.elements;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.TropicalFish;

import java.util.List;

public class UpgradeBar extends AbstractWidget {
    private final ResourceLocation TEXTURE = new ResourceLocation(MiningMadness.MODID, "textures/gui/elements/gui_machine_stat_bar.png");
    private final int x;
    private final int y;
    private final int width = 23;
    private final int height = 3;
    private final MachineInfusionEnum infusionEnum;
    private final Font font;

    private final int color1;
    private final int color2;

    private final BaseInfusionEntity entity;

    public UpgradeBar(Font font, int x, int y, BaseInfusionEntity entity, MachineInfusionEnum infusionEnum) {
        super(x, y, 23, 3, Component.empty());
        this.infusionEnum = infusionEnum;
        this.font = font;
        this.x = x;
        this.y = y;
        this.entity = entity;
        this.color1 = infusionEnum.getColor1();
        this.color2 = infusionEnum.getColor2();
    }

    protected void renderUpgradeBar(GuiGraphics pGuiGraphics){
        pGuiGraphics.blit(this.TEXTURE, this.x-1, this.y-1, 0, 0, this.width + 2, this.height + 2, this.width + 2, this.height + 2);

        int current = entity.getInfusionCapability().getInfusion(infusionEnum.getOpposite());
        int max = entity.getInfusionCapability().getInfusion(infusionEnum);

        int infusionBarWidth = width - (int)(width * (current / (float)max));
        pGuiGraphics.fillGradient(x, y, x + (width - infusionBarWidth), y + height, color1, color2);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderUpgradeBar(pGuiGraphics);

        int current = entity.getInfusionCapability().getInfusion(infusionEnum.getOpposite());
        int max = entity.getInfusionCapability().getInfusion(infusionEnum);

        if(this.isHovered()){
            List<Component> text = List.of(infusionEnum.getTooltipPrefix(), Component.literal(current + "/" + max));
            pGuiGraphics.renderComponentTooltip(this.font, text, pMouseX, pMouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}

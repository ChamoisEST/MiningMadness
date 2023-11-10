package com.chamoisest.miningmadness.client.screens.elements;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class UpgradeBar extends AbstractWidget {
    private final ResourceLocation TEXTURE = new ResourceLocation(MiningMadness.MODID, "textures/gui/elements/gui_machine_stat_bar.png");
    private final int x;
    private final int y;
    private final int width = 23;
    private final int height = 3;

    private final int color1;
    private final int color2;

    private final int current;
    private final int max;

    public UpgradeBar(int x, int y, int current, int max, MachineInfusionEnum infusionEnum) {
        super(x, y, 23, 3, Component.empty());
        this.x = x;
        this.y = y;
        this.current = current;
        this.max = max;
        this.color1 = infusionEnum.getColor1();
        this.color2 = infusionEnum.getColor2();
    }

    protected void renderUpgradeBar(GuiGraphics pGuiGraphics){
        pGuiGraphics.blit(this.TEXTURE, this.x-1, this.y-1, 0, 0, this.width + 2, this.height + 2, this.width + 2, this.height + 2);

        int infusionBarWidth = width - (int)(width * (current / (float)max));
        pGuiGraphics.fillGradient(x, y, x + (width - infusionBarWidth), y + height, color1, color2);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderUpgradeBar(pGuiGraphics);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}

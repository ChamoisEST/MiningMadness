package com.chamoisest.miningmadness.client.screen.elements;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class UpgradeBar extends AbstractWidget implements IDrawable {
    private final ResourceLocation TEXTURE = new ResourceLocation(MiningMadness.MOD_ID, "textures/gui/elements/gui_machine_stat_bar.png");
    private final int x;
    private final int y;
    private final int width = 23;
    private final int height = 3;

    private final int color1;
    private final int color2;

    private final int current;
    private final int max;

    public UpgradeBar(int x, int y, int current, int max, MachineUpgradeEnum upgradeEnum){
        super(x, y, 23, 3, Component.empty());
        this.x = x;
        this.y = y;
        this.current = current;
        this.max = max;
        this.color1 = upgradeEnum.getColor1();
        this.color2 = upgradeEnum.getColor2();

    }

    protected void renderUpgradeBar(PoseStack poseStack){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.TEXTURE);
        blit(poseStack, this.x-1, this.y-1, 0, 0, this.width + 2, this.height + 2, this.width + 2, this.height + 2);

        int stored = width - (int)(width*(current/(float)max));
        fillGradient(
                poseStack,
                x, y,
                x + (width -  stored), y + height,
                color1, color2
        );
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderUpgradeBar(pPoseStack);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        return false;
    }

    @Override
    public void draw(PoseStack poseStack) {
        IDrawable.super.draw(poseStack);
    }

    @Override
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        renderUpgradeBar(poseStack);
    }
}

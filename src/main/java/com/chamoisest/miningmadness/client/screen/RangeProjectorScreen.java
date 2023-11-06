package com.chamoisest.miningmadness.client.screen;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.base.BaseScreen;
import com.chamoisest.miningmadness.common.container.RangeProjectorMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RangeProjectorScreen extends BaseScreen<RangeProjectorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MiningMadness.MOD_ID,"textures/gui/blank_gui.png");

    public RangeProjectorScreen(RangeProjectorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        renderTexture(pPoseStack, TEXTURE);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
    }
}

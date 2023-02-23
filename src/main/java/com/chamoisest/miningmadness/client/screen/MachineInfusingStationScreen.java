package com.chamoisest.miningmadness.client.screen;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.base.BaseScreen;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.data.TextureData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.enums.TypeEnum;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MachineInfusingStationScreen extends BaseScreen<MachineInfusingStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MiningMadness.MOD_ID,"textures/gui/machine_infusing_station_gui.png");

    public MachineInfusingStationScreen(MachineInfusingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 175;
        this.imageHeight = 178;
        this.titleLabelY = this.titleLabelY - 1;
        this.inventoryLabelY = this.inventoryLabelY + 13;
    }

    @Override
    protected void init() {
        super.init();

        addWorkingButton(0);
        addRedstoneButton(1);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(poseStack, mouseX, mouseY, x, y, 153, 17, 14, 64);
        renderTooltips(poseStack, mouseX, mouseY, x, y, 0, TypeEnum.MACHINE);
        renderTooltips(poseStack, mouseX, mouseY, x, y, 1, TypeEnum.REDSTONE);

        if(this.menu.getSlot(36).hasItem()){
            ItemStack stack = menu.getSlot(36).getItem();
            Set<MachineUpgradeEnum> allowedUpgrades = MachineUpgradeData.getAllowedStats(stack);
            if(allowedUpgrades.size() >= 1){
                Map<MachineUpgradeEnum, Integer> itemUpgradeData = new HashMap<>();
                allowedUpgrades.forEach((upgrade) -> {
                    int value = menu.getSourceItemInfusedValue(upgrade, stack);
                    itemUpgradeData.put(upgrade, value);
                });

                renderUpgradeBars(poseStack, itemUpgradeData, stack.getItem(), 124, 17, mouseX, mouseY);
            }
        }

        renderProgress(poseStack, x, y);

        btnRunning.onUpdate(new TextureData().getTextureData(TypeEnum.MACHINE, menu.getWorking()));
        btnRedstone.onUpdate(new TextureData().getTextureData(TypeEnum.REDSTONE, menu.getRedstone()));
    }

    private void renderProgress(PoseStack stack, int x, int y){
        if(menu.isCrafting()){
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);

            float progress = menu.getProgressPercentage();
            float centralBarsProgress = Math.min(progress / 20 * 100, 100);
            int centralScaled = (int)(3 * (centralBarsProgress / 100));

            if(menu.hasCraftingIngredientInSlot(1)) blit(stack, 45, 34, 176, 32, 14, centralScaled);
            if(menu.hasCraftingIngredientInSlot(2)) blit(stack, 64 + (3 - centralScaled), 42, 176 + (3 - centralScaled), 18, centralScaled, 14);
            if(menu.hasCraftingIngredientInSlot(3)) blit(stack, 45, 61 + (3 - centralScaled), 176, 49  + (3 - centralScaled), 14, centralScaled);
            if(menu.hasCraftingIngredientInSlot(4)) blit(stack, 37, 42, 176, 35, centralScaled, 14);

            if(progress > 20){
                float bottomBarProgress = (progress - 20) / 70 * 100;
                int bottomScaledX = (int)(22 * (bottomBarProgress / 100));
                int bottomScaledY = (int)Math.min(18 * (bottomBarProgress / 70), 18);

                blit(stack, 62, 59, 176, 0, bottomScaledX, bottomScaledY);

            }
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

        renderTexture(pPoseStack, TEXTURE);
        assignEnergyInfoArea(153, 17);
        energyInfoArea.draw(pPoseStack);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}

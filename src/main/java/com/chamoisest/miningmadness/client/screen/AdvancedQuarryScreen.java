package com.chamoisest.miningmadness.client.screen;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.base.BaseScreen;
import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.data.TextureData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.enums.TypeEnum;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class AdvancedQuarryScreen extends BaseScreen<AdvancedQuarryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MiningMadness.MOD_ID,"textures/gui/advanced_quarry_gui.png");

    private static final Item blockItem = MMBlocks.ADVANCED_QUARRY.get().asItem();

    public AdvancedQuarryScreen(AdvancedQuarryMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();

        addWorkingButton(0);
        addRedstoneButton(1);


        addInfoAreaButton(140);

    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        Map<MachineUpgradeEnum, Integer> upgradeBars = new HashMap<>();
        upgradeBars.put(MachineUpgradeEnum.SPEED, menu.getSpeedInfused());
        upgradeBars.put(MachineUpgradeEnum.EFFICIENCY, menu.getEfficiencyInfused());
        upgradeBars.put(MachineUpgradeEnum.ENERGY_CAPACITY, menu.getEnergyCapacityInfused());
        upgradeBars.put(MachineUpgradeEnum.FORTUNE, menu.getFortuneInfused());
        upgradeBars.put(MachineUpgradeEnum.RANGE, menu.getRangeInfused());
        upgradeBars.put(MachineUpgradeEnum.SILK_TOUCH, menu.getSilkTouchInfused());

        renderUpgradeBars(poseStack, upgradeBars, blockItem, 124, 17, mouseX, mouseY);

        renderEnergyAreaTooltips(poseStack, mouseX, mouseY, x, y, 153, 11, 14, 64);
        renderTooltips(poseStack, mouseX, mouseY, x, y, 0, TypeEnum.MACHINE);
        renderTooltips(poseStack, mouseX, mouseY, x, y, 1, TypeEnum.REDSTONE);
        renderTooltips(poseStack, mouseX, mouseY, x, y, 2, TypeEnum.RANGE);

        renderMachineInfoAreaTooltips(poseStack, mouseX, mouseY, x, y, -24, 140, 24, 24);

        btnRunning.onUpdate(new TextureData().getTextureData(TypeEnum.MACHINE, menu.getWorking()));
        btnRedstone.onUpdate(new TextureData().getTextureData(TypeEnum.REDSTONE, menu.getRedstone()));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

        renderTexture(pPoseStack, TEXTURE);
        assignEnergyInfoArea(153, 11);
        energyInfoArea.draw(pPoseStack);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}

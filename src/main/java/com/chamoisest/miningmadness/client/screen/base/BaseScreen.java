package com.chamoisest.miningmadness.client.screen.base;

import com.chamoisest.miningmadness.client.screen.elements.TextureButton;
import com.chamoisest.miningmadness.client.screen.elements.UpgradeBar;
import com.chamoisest.miningmadness.client.screen.renderer.EnergyInfoArea;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.data.TextureData;
import com.chamoisest.miningmadness.data.TooltipData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.enums.TextureEnum;
import com.chamoisest.miningmadness.enums.TypeEnum;
import com.chamoisest.miningmadness.networking.MMMessages;
import com.chamoisest.miningmadness.networking.packet.RedstoneButtonSyncC2SPacket;
import com.chamoisest.miningmadness.networking.packet.RunningButtonSyncC2SPacket;
import com.chamoisest.miningmadness.networking.packet.ShowRangeButtonSyncC2SPacket;
import com.chamoisest.miningmadness.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseScreen<T extends BaseMenu<?>> extends AbstractContainerScreen<T> {
    protected EnergyInfoArea energyInfoArea;

    protected TextureButton btnRunning;
    protected TextureButton btnRedstone;
    protected TextureButton btnShowRange;

    protected TextureButton widgetInfo;

    protected T menu;

    public BaseScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();
    }

    protected void assignEnergyInfoArea(int xOffset, int yOffset) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + xOffset, y + yOffset, this.menu.getEnergy(), this.menu.getMaxEnergy());
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

    }

    protected void renderTexture(PoseStack pPoseStack, ResourceLocation texture){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    protected void renderEnergyAreaTooltips(PoseStack poseStack, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, width, height)) {
            renderTooltip(poseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    protected void renderTooltips(PoseStack poseStack, int mouseX, int mouseY, int x, int y, int id, TypeEnum type) {
        int tooltip = 0;
        tooltip = switch (type){
            case MACHINE -> menu.getStatusTooltip();
            case REDSTONE -> menu.getRedstoneTooltip();
            case RANGE -> menu.getShowRangeTooltip();
        };

        if(isMouseAboveArea(mouseX, mouseY, x, y, -24, 4 + id * 25, 24, 24)) {
            renderTooltip(poseStack, getErrorList(tooltip),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    protected void renderMachineInfoAreaTooltips(PoseStack poseStack, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, width, height)) {
            renderTooltip(poseStack, getMachineInfoAreaTooltips(),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Deprecated
    protected void renderUpgradeBar(PoseStack poseStack, int x, int y, int current, int max, MachineUpgradeEnum upgradeEnum, int mouseX, int mouseY, int guiX, int guiY){
            new UpgradeBar(x, y, current, max, upgradeEnum).draw(poseStack);

            if(isMouseAboveArea(mouseX, mouseY, guiX, guiY, x, y, 23, 3)) {
                renderTooltip(poseStack, List.of(Component.literal(upgradeEnum.getTooltipPrefix() + ": " + current + "/" + max)),
                        Optional.empty(), mouseX - guiX, mouseY - guiY);
            }
    }

    protected void renderUpgradeBars(PoseStack poseStack, Map<MachineUpgradeEnum, Integer> upgradeData, Item item, int startX, int startY, int mouseX, int mouseY){
        int guiX = (width - imageWidth) / 2;
        int guiY = (height - imageHeight) / 2;
        Iterator<Map.Entry<MachineUpgradeEnum, Integer>> iterator = upgradeData.entrySet().iterator();
        int i = 0;

        while(iterator.hasNext()){
            Map.Entry<MachineUpgradeEnum, Integer> entry = iterator.next();

            int current = entry.getValue();
            MachineUpgradeEnum upgradeEnum = entry.getKey();

            int max = MachineUpgradeData.getMachineBaseStats(item, upgradeEnum);

            new UpgradeBar(startX, startY + 6 * i, current, max, upgradeEnum).draw(poseStack);

            if(isMouseAboveArea(mouseX, mouseY, guiX, guiY, startX, startY + 6 * i, 23, 3)) {
                renderTooltip(poseStack, List.of(Component.literal(upgradeEnum.getTooltipPrefix().getString() + ": " + current + "/" + max)),
                        Optional.empty(), mouseX - guiX, mouseY - guiY);
            }
            i++;
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private List<Component> getErrorList(int error){
        return List.of(new TooltipData().getToolTipComponent(error));
    }

    private List<Component> getMachineInfoAreaTooltips(){
        return List.of(
                Component.translatable("tooltip.miningmadness.energy_usage")
                    .append(": ")
                    .append(Component.literal(menu.getEnergyPerTick() + " "))
                    .append(Component.translatable("tooltip.miningmadness.fe_t")),
                Component.translatable("tooltip.miningmadness.speed")
                    .append(": ")
                    .append(Component.literal(menu.getOperationFrequency() + " "))
                    .append(Component.translatable("tooltip.miningmadness.ticks"))
        );
    }

    protected RunningButtonSyncC2SPacket getRunningButtonUpdatePacket(int running){
        return new RunningButtonSyncC2SPacket(running, menu.getBlockEntity().getBlockPos());
    }

    protected RedstoneButtonSyncC2SPacket getRedstoneButtonUpdatePacket(int redstone){
        return new RedstoneButtonSyncC2SPacket(redstone, menu.getBlockEntity().getBlockPos());
    }

    protected ShowRangeButtonSyncC2SPacket getShowRangeButtonUpdatePacket(int showRange){
        return new ShowRangeButtonSyncC2SPacket(showRange, menu.getBlockEntity().getBlockPos());
    }

    protected void addRedstoneButton(int id){
        if(menu != null) {
            btnRedstone = addRenderableWidget(new TextureButton(leftPos - 24, topPos + 4 + id * 25, 24, 24, new TextureData().getTextureData(TypeEnum.REDSTONE, menu.getNewRedstone()), (p) -> {
                int redstone = menu.getNewRedstone();
                MMMessages.sendToServer(getRedstoneButtonUpdatePacket(redstone));
            }));
        }
    }

    protected void addWorkingButton(int id){
        if(menu != null) {
            btnRunning = addRenderableWidget(new TextureButton(leftPos - 24, topPos + 4 + id * 25, 24, 24, new TextureData().getTextureData(TypeEnum.MACHINE, menu.getNewWorking()), (p) -> {
                int running = menu.getNewWorking();
                MMMessages.sendToServer(getRunningButtonUpdatePacket(running));
            }));
        }
    }

    protected void addRangeButton(int id){
        if(menu != null) {
            btnShowRange = addRenderableWidget(new TextureButton(leftPos - 24, topPos + 4 + id * 25, 24, 24, new TextureData().getTextureData(TypeEnum.RANGE, menu.getNewShowRange()), (p) -> {
                int showRange = menu.getNewShowRange();
                MMMessages.sendToServer(getShowRangeButtonUpdatePacket(showRange));
            }));
        }
    }

    protected void addInfoAreaButton(int yOffset){
        if(menu != null) {
            widgetInfo = addRenderableWidget(new TextureButton(leftPos - 24, topPos + yOffset, 24, 24, TextureEnum.INFO_WIDGET, (p) -> {

            }));
        }
    }
}

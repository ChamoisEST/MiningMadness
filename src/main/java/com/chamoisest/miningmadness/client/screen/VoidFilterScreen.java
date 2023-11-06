package com.chamoisest.miningmadness.client.screen;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.base.BaseScreen;
import com.chamoisest.miningmadness.common.container.VoidFilterMenu;
import com.chamoisest.miningmadness.common.container.customslot.FilterVoidSlot;
import com.chamoisest.miningmadness.networking.MMMessages;
import com.chamoisest.miningmadness.networking.packet.GhostFilterVoidC2SPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VoidFilterScreen extends BaseScreen<VoidFilterMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MiningMadness.MOD_ID,"textures/gui/void_filter_gui.png");

    public VoidFilterScreen(VoidFilterMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();

        addSidednessButton(0);
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

//    @Override
//    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        if(hoveredSlot == null || !(hoveredSlot instanceof FilterVoidSlot)){
//            return super.mouseClicked(pMouseX, pMouseY, pButton);
//        }
//
//        ItemStack stack = this.menu.getCarried();
//        stack = stack.copy();
//        hoveredSlot.set(stack);
//        MMMessages.sendToServer(new GhostFilterVoidC2SPacket(hoveredSlot.index, stack));
//
//        return true;
//    }

}

package com.chamoisest.miningmadness.client.screen.elements;

import com.chamoisest.miningmadness.enums.TextureEnum;
import com.chamoisest.miningmadness.registry.TextureRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class TextureButton extends ExtendedButton {
    private TextureEnum texture;

    public TextureButton(int xPos, int yPos, int width, int height, String displayString, OnPress handler) {
        super(xPos, yPos, width, height, Component.translatable(displayString), handler);
    }

    public TextureButton(int xPos, int yPos, int width, int height, TextureEnum tid, OnPress handler) {
        super(xPos, yPos, width, height, Component.translatable(""), handler);
        this.texture = tid;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderButton(poseStack, mouseX, mouseY, partialTick);
        Minecraft mc = Minecraft.getInstance();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TextureRegistry.WIDGETS);

        if(this.texture != null){
            this.blit(poseStack,
                    this.x + 4, this.y + 4,
                    this.texture.getX(), this.texture.getY(),
                    16, 16);
        }

        this.renderBg(poseStack, mc, mouseX, mouseY);
    }

    private void setTexture(TextureEnum texture){
        this.texture = texture;
    }

    //Hiljem teha ümber, et saaks kasutada ka teiste block entititega.
    public void onUpdate(TextureEnum tid){
        setTexture(tid);
    }
}

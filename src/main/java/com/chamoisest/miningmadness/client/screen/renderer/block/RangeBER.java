package com.chamoisest.miningmadness.client.screen.renderer.block;

import com.chamoisest.miningmadness.common.block.entity.base.BaseQuarryBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;

public class RangeBER<T extends BaseQuarryBlockEntity> implements BlockEntityRenderer<T> {
    private final BlockEntityRendererProvider.Context context;

    private static final RenderType AREA_TYPE = createRenderType();

    public static RenderType createRenderType() {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader))
                .setTransparencyState(new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                }, () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                })).createCompositeState(true);
        return RenderType.create("working_area_render", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, state);
    }

    public RangeBER(BlockEntityRendererProvider.Context context){
        this.context = context;
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getShowRange() != 1) return;
        VoxelShape shape = pBlockEntity.rangeManager.getShape();
        BlockPos blockPos = pBlockEntity.getBlockPos();

        RenderSystem.lineWidth(2.5F);

        pPoseStack.pushPose();

        if(pBlockEntity.drawTopRangeHelperByY > 0){
            Color helperColor = new Color(0.227F, 0.003F, 0.252f, 0.8f);
            renderFaces(pPoseStack, pBufferSource, shape.move(0, 3, 0).bounds().setMinY(shape.bounds().maxY), -blockPos.getX(), -blockPos.getY(), -blockPos.getZ(), (float) helperColor.getRed() / 255f, (float) helperColor.getGreen() / 255f, (float) helperColor.getBlue() / 255f, 0.3F);
        }else{
            Color color = new Color(0.003F, 0.227F, 0.252f, 0.8f);
            renderFaces(pPoseStack, pBufferSource, shape.bounds(), -blockPos.getX(), -blockPos.getY(), -blockPos.getZ(), (float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, 0.3F);
        }

        pPoseStack.popPose();

    }

    private void renderFaces(PoseStack stack, MultiBufferSource renderTypeBuffer, AABB pos, double x, double y, double z, float red, float green, float blue, float alpha) {

        Matrix4f matrix = stack.last().pose();

        VertexConsumer buffer;
        buffer = renderTypeBuffer.getBuffer(AREA_TYPE);

        float x1 = (float) (pos.minX + x);
        float x2 = (float) (pos.maxX + x);
        float y1 = (float) (pos.minY + y);
        float y2 = (float) (pos.maxY + y);
        float z1 = (float) (pos.minZ + z);
        float z2 = (float) (pos.maxZ + z);

        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).endVertex();

        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).endVertex();


        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).endVertex();

        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).endVertex();


        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha).endVertex();

        buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).endVertex();
        buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha).endVertex();
    }
}

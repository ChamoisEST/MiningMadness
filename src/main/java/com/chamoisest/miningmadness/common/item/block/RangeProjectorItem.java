package com.chamoisest.miningmadness.common.item.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RangeProjectorItem extends BlockItem {
    public RangeProjectorItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.miningmadness.range_projector_info"));

        } else {
            components.add(Component.translatable("tooltip.miningmadness.press")
                    .append(Component.literal(" SHIFT ").withStyle(ChatFormatting.AQUA))
                    .append(Component.translatable("tooltip.miningmadness.for_more_info"))
            );
        }

        super.appendHoverText(stack, level, components, flag);
    }
}

package com.chamoisest.miningmadness.common.item.block;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class BaseMachineItem extends BlockItem {
    public BaseMachineItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.miningmadness.current_stats"));

            Set<MachineUpgradeEnum> machineBaseStats = MachineUpgradeData.getAllowedStats(stack);
            machineBaseStats.forEach((stat) -> {
                int statValue = stack.getOrCreateTag().getInt(stat.getNBTName());
                int maxValue = MachineUpgradeData.getMachineBaseStats(stack, stat);
                components.add(stat.getTooltipPrefix()
                        .append(Component.literal(": " + statValue + "/" + maxValue))
                );
            });

        } else {
            components.add(Component.translatable("tooltip.miningmadness.press")
                    .append(Component.literal(" SHIFT ").withStyle(ChatFormatting.AQUA))
                    .append(Component.translatable("tooltip.miningmadness.for_more_info"))
            );
        }

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof BaseMachineBlockEntity entity){
            entity.ENERGY_STORAGE.setEnergy(pStack.getOrCreateTag().getInt("energy"));

            Set<MachineUpgradeEnum> machineBaseStats = MachineUpgradeData.getAllowedStats(pState.getBlock().asItem());
            machineBaseStats.forEach((stat) -> {
                entity.setInfused(stat, pStack.getOrCreateTag().getInt(stat.getNBTName()));
            });
        }
        return super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
    }
}

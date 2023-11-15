package com.chamoisest.miningmadness.common.items.base;

import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.interfaces.IInfusionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseInfusionBlockItem extends BlockItem{

    public static InfusionCapability infusionCapability;
    public BaseInfusionBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        infusionCapability = new InfusionCapability() {
            @Override
            public void onInfusionChanged(MachineInfusionEnum type, int value) {

            }
        };
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
        return super.updateCustomBlockEntityTag(pPos, pLevel, pPlayer, pStack, pState);
    }
}

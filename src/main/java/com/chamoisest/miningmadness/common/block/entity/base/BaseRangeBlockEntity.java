package com.chamoisest.miningmadness.common.block.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseRangeBlockEntity extends BaseMachineBlockEntity{
    public BaseRangeBlockEntity(BlockPos pos, BlockState state, BlockEntityType beType, int invSize, int baseMaxTransfer, int baseEnergyReq) {
        super(pos, state, beType, invSize, baseMaxTransfer, baseEnergyReq);
    }
}

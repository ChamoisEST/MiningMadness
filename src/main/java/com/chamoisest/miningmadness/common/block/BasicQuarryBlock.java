package com.chamoisest.miningmadness.common.block;

import com.chamoisest.miningmadness.common.block.base.BaseMachineBlock;
import com.chamoisest.miningmadness.common.block.entity.BasicQuarryBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BasicQuarryBlock extends BaseMachineBlock {

    public BasicQuarryBlock(Properties properties) {
        super(properties);
    }

    /* BLOCK ENTITY*/

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BasicQuarryBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, MMBlockEntities.BASIC_QUARRY.get(), BasicQuarryBlockEntity::tick);
    }
}

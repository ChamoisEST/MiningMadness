package com.chamoisest.miningmadness.common.blocks.quarry;

import com.chamoisest.miningmadness.common.blockentities.base.BaseMenuEntity;
import com.chamoisest.miningmadness.common.blockentities.quarry.BasicQuarryEntity;
import com.chamoisest.miningmadness.common.blocks.base.BaseDirectionalBlock;
import com.chamoisest.miningmadness.setup.BlockEntitySetup;
import com.chamoisest.miningmadness.setup.BlockSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BasicQuarry extends BaseDirectionalBlock{
    public BasicQuarry(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if(pPlayer instanceof ServerPlayer serverPlayer){
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if(entity instanceof BaseMenuEntity) {
                    NetworkHooks.openScreen(serverPlayer, (MenuProvider) entity, pPos);
                }
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BasicQuarryEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntitySetup.BASIC_QUARRY.get(), BasicQuarryEntity::tick);
    }

    @Override
    public @Nullable <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return null;
    }
}

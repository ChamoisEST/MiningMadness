package com.chamoisest.miningmadness.common.block;

import com.chamoisest.miningmadness.common.block.base.BaseCustomEntityBlock;
import com.chamoisest.miningmadness.common.block.entity.RangeProjectorBlockEntity;
import com.chamoisest.miningmadness.common.block.entity.base.BaseRangeBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class RangeProjectorBlock extends BaseCustomEntityBlock {

    public RangeProjectorBlock(Properties pProperties) {
        super(pProperties);
    }

    //private static final VoxelShape SHAPE = Block.box(0,0,0,7,7,7);

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(3,0,5,13,13,11)

    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            BlockEntity under = pLevel.getBlockEntity(pPos.below());
            if(entity instanceof MenuProvider) {
                if(under instanceof BaseRangeBlockEntity) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (MenuProvider) entity, pPos);
                }else{
                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.sendSystemMessage(Component.translatable("chatmessage.miningmadness.rangeprojector_no_connected_entity"));
                }
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    /* BLOCK ENTITY*/

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RangeProjectorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, MMBlockEntities.RANGE_PROJECTOR.get(), RangeProjectorBlockEntity::tick);
    }
}

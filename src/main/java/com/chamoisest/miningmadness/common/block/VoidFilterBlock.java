package com.chamoisest.miningmadness.common.block;

import com.chamoisest.miningmadness.common.block.base.BaseCustomEntityBlock;
import com.chamoisest.miningmadness.common.block.entity.VoidFilterBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class VoidFilterBlock extends BaseCustomEntityBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public VoidFilterBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE));
    }

    //private static final VoxelShape SHAPE = Block.box(0,0,0,7,7,7);

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(2,2,2,14,14,14)

    ).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateConnections(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            BlockState newBlockState = updateConnections(pState, pLevel, pPos);
            pLevel.setBlockAndUpdate(pPos, newBlockState);
        }
    }

    protected static BlockState updateConnections(BlockState pState, Level pLevel, BlockPos pPos){
        BlockState newBlockState = pState;
        //Update blockstate if inventories connected.

        BlockEntity be_above = pLevel.getBlockEntity(pPos.above());
        BlockEntity be_below = pLevel.getBlockEntity(pPos.below());
        BlockEntity be_west = pLevel.getBlockEntity(pPos.west());
        BlockEntity be_east = pLevel.getBlockEntity(pPos.east());
        BlockEntity be_north = pLevel.getBlockEntity(pPos.north());
        BlockEntity be_south = pLevel.getBlockEntity(pPos.south());

        newBlockState = updateBlockState(be_above, newBlockState, BlockStateProperties.UP);
        newBlockState = updateBlockState(be_below, newBlockState, BlockStateProperties.DOWN);
        newBlockState = updateBlockState(be_west, newBlockState, BlockStateProperties.WEST);
        newBlockState = updateBlockState(be_east, newBlockState, BlockStateProperties.EAST);
        newBlockState = updateBlockState(be_north, newBlockState, BlockStateProperties.NORTH);
        newBlockState = updateBlockState(be_south, newBlockState, BlockStateProperties.SOUTH);

        return newBlockState;
    }

    protected static BlockState updateBlockState(BlockEntity entity, BlockState state, BooleanProperty direction){

        if(entity instanceof MenuProvider){
            state = state.setValue(direction, Boolean.TRUE);
        }else{
            state = state.setValue(direction, Boolean.FALSE);
        }

        return state;
    }

    /* BLOCK ENTITY*/

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidFilterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, MMBlockEntities.VOID_FILTER.get(), VoidFilterBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.hasBlockEntity() && (!pState.is(pNewState.getBlock()) || !pNewState.hasBlockEntity())) {
            pLevel.removeBlockEntity(pPos);
        }
    }
}

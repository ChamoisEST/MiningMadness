package com.chamoisest.miningmadness.common.blocks.quarry;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screens.elements.UpgradeBar;
import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import com.chamoisest.miningmadness.common.blockentities.base.BaseMenuEntity;
import com.chamoisest.miningmadness.common.blockentities.quarry.BasicQuarryEntity;
import com.chamoisest.miningmadness.common.blocks.base.BaseDirectionalBlock;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.NBTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BasicQuarry extends BaseDirectionalBlock{
    public BasicQuarry(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        BlockEntity entity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        Item dropItem = pState.getBlock().asItem();

        List<ItemStack> drops = super.getDrops(pState, pParams);

        if(entity instanceof BaseInfusionEntity infusionEntity){
            drops.stream()
                    .filter(e -> e.getItem() == dropItem)
                    .findFirst()
                    .ifPresent(e -> {
                        e.getOrCreateTag().putInt(NBTTags.ENERGY_TAG, infusionEntity.getEnergyHandler().getEnergyStored());

                        infusionEntity.getCapability(MiningMadness.INFUSION_CAPABILITY).ifPresent(cap -> {

                            EnumMap<MachineInfusionEnum, Integer> activeInfusions = cap.getActiveInfusionMap();
                            for (Map.Entry<MachineInfusionEnum, Integer> entry : activeInfusions.entrySet()) {

                                MachineInfusionEnum type = entry.getKey();
                                int value = entry.getValue();

                                cap.setInfusion(type, value);
                            }
                        });
                    });
        }
        return drops;
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
        if (pLevel.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof BasicQuarryEntity entity) {
                entity.tick();
            }
        };
    }
}

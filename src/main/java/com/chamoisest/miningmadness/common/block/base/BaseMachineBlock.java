package com.chamoisest.miningmadness.common.block.base;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class BaseMachineBlock extends BaseCustomEntityBlock{
    protected BaseMachineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        BlockEntity blockEntity = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        Item dropItem = pState.getBlock().asItem();

        List<ItemStack> drops = super.getDrops(pState, pBuilder);
        if(blockEntity instanceof BaseMachineBlockEntity entity){
            drops.stream()
                    .filter(e -> e.getItem() == dropItem)
                    .findFirst()
                    .ifPresent(e -> {
                        e.getOrCreateTag().putInt("energy", entity.getEnergyStorage().getEnergyStored());

                        Set<MachineUpgradeEnum> machineBaseStats = MachineUpgradeData.getAllowedStats(pState.getBlock().asItem());
                        machineBaseStats.forEach((stat) -> {
                            e.getOrCreateTag().putInt(stat.getNBTName(), entity.getInfused(stat));
                        });
                    });
        }

        return drops;
    }
}

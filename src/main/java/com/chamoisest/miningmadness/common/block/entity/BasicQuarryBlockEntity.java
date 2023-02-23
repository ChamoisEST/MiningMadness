package com.chamoisest.miningmadness.common.block.entity;

import com.chamoisest.miningmadness.client.screen.BasicQuarryMenu;
import com.chamoisest.miningmadness.common.block.entity.base.BaseQuarryBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.util.RangeManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicQuarryBlockEntity extends BaseQuarryBlockEntity {

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> BasicQuarryBlockEntity.this.ENERGY_STORAGE.getEnergyStored();
                case 1 -> BasicQuarryBlockEntity.this.ENERGY_STORAGE.getMaxEnergyStored();
                case 2 -> BasicQuarryBlockEntity.this.getWorking();
                case 3 -> BasicQuarryBlockEntity.this.getStatusTooltip();
                case 4 -> BasicQuarryBlockEntity.this.getRedstone();
                case 5 -> BasicQuarryBlockEntity.this.getRedstoneTooltip();
                case 8 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.SPEED);
                case 9 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.ENERGY_CAPACITY);
                case 10 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.EFFICIENCY);
                case 11 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.FORTUNE);
                case 12 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.SILK_TOUCH);
                case 13 -> BasicQuarryBlockEntity.this.getInfused(MachineUpgradeEnum.RANGE);
                case 14 -> BasicQuarryBlockEntity.this.getShowRange();
                case 15 -> BasicQuarryBlockEntity.this.getShowRangeTooltip();
                case 6 -> BasicQuarryBlockEntity.this.calculateEnergyReqPerOperation();
                case 7 -> BasicQuarryBlockEntity.this.calculateEnergyPerTick();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0, 1, 3, 5, 6, 7, 15 -> throw new UnsupportedOperationException("Key '" + index + "' has no setter!");
                case 2 -> updateWorking(value);
                case 4 -> updateRedstone(value);
                case 8 -> setInfused(MachineUpgradeEnum.SPEED, value);
                case 9 -> setInfused(MachineUpgradeEnum.ENERGY_CAPACITY, value);
                case 10 -> setInfused(MachineUpgradeEnum.EFFICIENCY, value);
                case 11 -> setInfused(MachineUpgradeEnum.FORTUNE, value);
                case 12 -> setInfused(MachineUpgradeEnum.SILK_TOUCH, value);
                case 13 -> setInfused(MachineUpgradeEnum.RANGE, value);
                case 14 -> updateShowRange(value);
                default -> throw new UnsupportedOperationException("Key '" + index + "' not found!");
            }
            ;
        }

        @Override
        public int getCount() {
            return 16;
        }
    };
    public BasicQuarryBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, MMBlockEntities.BASIC_QUARRY.get(), 12, 512, 60);
        this.displayNameTranslatable = "Basic Quarry";
        this.baseEnergyReqForOperation = 1000;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BasicQuarryMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BasicQuarryBlockEntity pEntity) {
            if(pEntity.rangeManager == null){
                pEntity.rangeManager = new RangeManager(
                        pEntity,
                        pEntity.calculateRange(),
                        pEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getOpposite(),
                        1, -1, (pEntity.getBlockPos().getY() - 1 -(-64))
                );
            }

            pEntity.ENERGY_STORAGE.setMaxEnergyStored(pEntity.calculateEnergyCapacity());
            if(pEntity.finished == 1){
                pEntity.updateWorking(2);
                setStatusTooltip(pEntity, 8);
            }else if (!hasEnoughEnergy(pEntity)) {
                pEntity.updateWorking(2);
                setStatusTooltip(pEntity, 1);
                return;
            } else if(hasError(pEntity) && checkInventoryFull(pEntity)){
                setInventoryFull(pEntity);
                return;
            }else if (hasError(pEntity)) {
                pEntity.updateWorking(0);
                setStatusTooltip(pEntity, 4);
            }

            if (isWorking(pEntity) && checkRedstone(level, pEntity)) {
                extractEnergy(pEntity);
                if (increaseTick(pEntity)) {
                    if(!level.isClientSide()) {
                        mine(pEntity, level);
                    }
                }
                setChanged(level, blockPos, blockState);
            }
    }


}

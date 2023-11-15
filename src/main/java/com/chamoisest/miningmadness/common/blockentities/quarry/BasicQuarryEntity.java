package com.chamoisest.miningmadness.common.blockentities.quarry;

import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import com.chamoisest.miningmadness.common.containers.BasicQuarryMenu;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.setup.BlockEntitySetup;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicQuarryEntity extends BaseInfusionEntity {

    protected final ContainerData data = new ContainerData() {

        @Override
        public int get(int pIndex) {
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return BasicQuarryMenu.DATA_SLOTS;
        }
    };

    public BasicQuarryEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitySetup.BASIC_QUARRY.get(), pPos, pBlockState, BasicQuarryMenu.SLOTS);
        setDisplayName("Basic Quarry");
    }

    @Override
    public void onContentsChanged(int slot) {
        setChanged();
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BasicQuarryMenu(pPlayerInventory, this, this.data, pContainerId);
    }

    @Override
    public void activateInfusionTypes() {
        activateInfusion(MachineInfusionEnum.MAX_SPEED, 360);
        activateInfusion(MachineInfusionEnum.MAX_EFFICIENCY, 360);
        activateInfusion(MachineInfusionEnum.MAX_FORTUNE, 360);
        activateInfusion(MachineInfusionEnum.MAX_RANGE, 45);
        activateInfusion(MachineInfusionEnum.MAX_ENERGY_CAPACITY, 100);
        activateInfusion(MachineInfusionEnum.MAX_SILK_TOUCH, 1);
    }

    public void tick(){
        if(this.level == null) return;
    }

    @Override
    public int getCapacity() {
        return 100000;
    }

    @Override
    public int getMaxTransfer() {
        return 512;
    }


}

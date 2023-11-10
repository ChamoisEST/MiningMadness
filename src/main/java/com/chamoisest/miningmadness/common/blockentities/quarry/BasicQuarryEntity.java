package com.chamoisest.miningmadness.common.blockentities.quarry;

import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapabilityProvider;
import com.chamoisest.miningmadness.common.containers.BasicQuarryMenu;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.setup.BlockEntitySetup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BasicQuarryEntity extends BaseInfusionEntity{

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
        getInfusionCapability().addInfusion(MachineInfusionEnum.FORTUNE, 50);
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
        setMaxSpeed(360);
        setMaxEfficiency(360);
        setMaxFortune(360);
        setMaxRange(45);
        setMaxEnergyCapacityInfusion(100);
        setMaxSilkTouch();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BasicQuarryEntity pEntity){

    }
}

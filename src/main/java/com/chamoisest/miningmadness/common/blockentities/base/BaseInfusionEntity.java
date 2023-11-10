package com.chamoisest.miningmadness.common.blockentities.base;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.capabilities.infusion.IInfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapabilityProvider;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class BaseInfusionEntity extends BaseMenuEntity{
    private static final String INFUSION_TAG = "infusion";
    public final InfusionCapability infusionCapability = new InfusionCapability();
    protected LazyOptional<IInfusionCapability> lazyInfusionHandler = LazyOptional.of(() -> infusionCapability);

    public BaseInfusionEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int invSize) {
        super(pType, pPos, pBlockState, invSize);
        activateInfusionTypes();
    }

    public InfusionCapability getInfusionCapability(){
        return infusionCapability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == MiningMadness.INFUSION_CAPABILITY){
            return lazyInfusionHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public abstract void activateInfusionTypes();

    @Override
    public void onLoad() {
        super.onLoad();
        lazyInfusionHandler = LazyOptional.of(() -> infusionCapability);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyInfusionHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(INFUSION_TAG, infusionCapability.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains(INFUSION_TAG)){
            infusionCapability.deserializeNBT(pTag.getCompound(INFUSION_TAG));
        }
    }

    //This automatically activates the use of that infusion type on the entity
    public void setMaxRange(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_RANGE, maxValue);
    }

    public void setMaxSpeed(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_SPEED, maxValue);
    }

    public void setMaxEnergyCapacityInfusion(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_ENERGY_CAPACITY, maxValue);
    }

    public void setMaxEfficiency(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_EFFICIENCY, maxValue);
    }

    public void setMaxFortune(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_FORTUNE, maxValue);
    }

    public void setMaxSilkTouch(){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_SILK_TOUCH, 1);
    }
}

package com.chamoisest.miningmadness.common.capabilities.infusion;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class InfusionCapabilityProvider implements ICapabilityProvider {

    private InfusionCapability infusionCapability = null;
    private final LazyOptional<InfusionCapability> opt = LazyOptional.of(this::createInfusionCapability);

    public InfusionCapabilityProvider(){

    }

    @Nonnull
    private InfusionCapability createInfusionCapability() {
        if(infusionCapability == null){
            infusionCapability = new InfusionCapability(){
                @Override
                public void onInfusionChanged(MachineInfusionEnum type, int value) {

                }
            };
        }
        return infusionCapability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == MiningMadness.INFUSION_CAPABILITY){
            return opt.cast();
        }
        return LazyOptional.empty();
    }
}

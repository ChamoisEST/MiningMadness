package com.chamoisest.miningmadness.common.blockentities.interfaces;

import com.chamoisest.miningmadness.common.capabilities.infusion.IInfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraftforge.common.util.LazyOptional;

public interface IInfusable {
    String INFUSION_TAG = "infusion";
    InfusionCapability infusionCapability = new InfusionCapability();
    LazyOptional<IInfusionCapability> lazyInfusionHandler = LazyOptional.of(() -> infusionCapability);

    default InfusionCapability getInfusionCapability(){
        return infusionCapability;
    }

    void activateInfusionTypes();
    void setInfusionChanged(MachineInfusionEnum type, int value);

    default void initializeInfusionPacket(MachineInfusionEnum maxType, int maxValue){
        setInfusionChanged(maxType.getOpposite(), 0);
        setInfusionChanged(maxType, maxValue);
    }

    //This automatically activates the use of that infusion type on the entity
    default void setMaxRange(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_RANGE, maxValue);
        initializeInfusionPacket(MachineInfusionEnum.MAX_RANGE, maxValue);
    }

    default void setMaxSpeed(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_SPEED, maxValue);
        initializeInfusionPacket(MachineInfusionEnum.MAX_SPEED, maxValue);
    }

    default void setMaxEnergyCapacityInfusion(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_ENERGY_CAPACITY, maxValue);
        initializeInfusionPacket(MachineInfusionEnum.MAX_ENERGY_CAPACITY, maxValue);
    }

    default void setMaxEfficiency(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_EFFICIENCY, maxValue);
        initializeInfusionPacket(MachineInfusionEnum.MAX_EFFICIENCY, maxValue);
    }

    default void setMaxFortune(int maxValue){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_FORTUNE, maxValue);
        initializeInfusionPacket(MachineInfusionEnum.MAX_FORTUNE, maxValue);
    }

    default void setMaxSilkTouch(){
        infusionCapability.setMaxInfusion(MachineInfusionEnum.MAX_SILK_TOUCH, 1);
        initializeInfusionPacket(MachineInfusionEnum.MAX_SILK_TOUCH, 1);
    }
}

package com.chamoisest.miningmadness.common.blockentities.interfaces;

import com.chamoisest.miningmadness.common.capabilities.infusion.IInfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraftforge.common.util.LazyOptional;

public interface IInfusable {
    void activateInfusionTypes();
    void setInfusionChanged(MachineInfusionEnum type, int value);

    default void initializeInfusionPacket(MachineInfusionEnum maxType, int maxValue){
        setInfusionChanged(maxType.getOpposite(), 0);
        setInfusionChanged(maxType, maxValue);
    }

    //This automatically activates the use of that infusion type on the entity
    void activateInfusion(MachineInfusionEnum maxType, int maxValue);
}

package com.chamoisest.miningmadness.common.capabilities.infusion;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.interfaces.IInfusionUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import java.util.ArrayList;
import java.util.List;

public class InfusionCapability implements IInfusionCapability, INBTSerializable<CompoundTag>, IInfusionUtil {

    public InfusionCapability() {

    }

    public int getInfusion(MachineInfusionEnum type){
        return getInfusionValue(type);
    }

    public void addInfusion(MachineInfusionEnum type, Integer valueToAdd){
        if(isCurrentValue(type)) {
            int maxValue = getMaxValueByType(type);
            int currentValue = getInfusionValue(type);
            int newCurrentValue = currentValue + valueToAdd;

            if (currentValue >= maxValue) {
                setInfusionValue(type, maxValue);
            } else if (newCurrentValue >= maxValue) {
                setInfusionValue(type, maxValue);
            } else {
                setInfusionValue(type, newCurrentValue);
            }
        }
    }

    public void reduceInfusion(MachineInfusionEnum type, Integer valueToReduce){
        if(isCurrentValue(type)) {
            int currentValue = getInfusionValue(type);
            int newCurrentValue = currentValue - valueToReduce;

            if (currentValue < 0) {
                setInfusionValue(type, 0);
            } else if (newCurrentValue <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, newCurrentValue);
            }
        }
    }

    public void setInfusion(MachineInfusionEnum type, Integer value){
        if(isCurrentValue(type)) {
            int maxValue = getMaxValueByType(type);
            if (value >= maxValue) {
                setInfusionValue(type, maxValue);
            } else if (value <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, value);
            }
        }
    }

    public void setMaxInfusion(MachineInfusionEnum type, Integer value){
        if(isMaxValue(type)) {
            if (value <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, value);
            }
        }
    }

    public boolean isTypeActive(MachineInfusionEnum type){
        if(isMaxValue(type)){
            return getInfusionValue(type) > 0;
        }else if(isCurrentValue(type)){
            int maxValue = getMaxValueByType(type);
            return maxValue > 0;
        }
        return false;
    }

    public List<MachineInfusionEnum> getActiveInfusions(){
        List<MachineInfusionEnum> activeList = new ArrayList<>();
        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            if(isCurrentValue(type)) continue;
            if(getInfusionValue(type) <= 0) continue;
            activeList.add(type);
        }
        return activeList;
    }

    public int getMaxValueByCurrent(MachineInfusionEnum type){
        if(isCurrentValue(type)){
            return getMaxValueByCurrent(type);
        }
        return -1;
    }

    public int getCurrentValueByMax(MachineInfusionEnum type){
        if(isMaxValue(type)){
            return getCurrentValueByMaxType(type);
        }
        return -1;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            nbt.putInt(getInfusionNBTTag(type), getInfusionValue(type));
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            setInfusionValue(type, nbt.getInt(getInfusionNBTTag(type)));
        }
    }
}

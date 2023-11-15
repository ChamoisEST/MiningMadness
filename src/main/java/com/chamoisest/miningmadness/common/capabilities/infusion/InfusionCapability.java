package com.chamoisest.miningmadness.common.capabilities.infusion;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.interfaces.IInfusionUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import java.util.ArrayList;
import java.util.List;

public abstract class InfusionCapability implements IInfusionCapability, INBTSerializable<CompoundTag>, IInfusionUtil {

    public InfusionCapability() {

    }

    public int getInfusion(MachineInfusionEnum type){
        return getInfusionValue(type);
    }

    public void addInfusion(MachineInfusionEnum type, Integer valueToAdd){
        if(type.isCurrentValue()) {
            int maxValue = getMaxValueByType(type);
            int currentValue = getInfusionValue(type);
            int newCurrentValue = currentValue + valueToAdd;
            int newValue = 0;

            if (currentValue >= maxValue) {
                setInfusionValue(type, maxValue);
                newValue = maxValue;
            } else if (newCurrentValue >= maxValue) {
                setInfusionValue(type, maxValue);
                newValue = maxValue;
            } else {
                setInfusionValue(type, newCurrentValue);
                newValue = newCurrentValue;
            }

            onInfusionChanged(type, newValue);
        }
    }

    public void reduceInfusion(MachineInfusionEnum type, Integer valueToReduce){
        if(type.isCurrentValue()) {
            int currentValue = getInfusionValue(type);
            int newCurrentValue = currentValue - valueToReduce;
            int newValue = 0;

            if (currentValue < 0) {
                setInfusionValue(type, 0);
            } else if (newCurrentValue <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, newCurrentValue);
                newValue = newCurrentValue;
            }
            onInfusionChanged(type, newValue);
        }
    }

    public void setInfusion(MachineInfusionEnum type, Integer value){
        if(type.isCurrentValue()) {
            int maxValue = getMaxValueByType(type);
            int newValue = 0;

            if (value >= maxValue) {
                setInfusionValue(type, maxValue);
                newValue = maxValue;
            } else if (value <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, value);
                newValue = value;
            }
            onInfusionChanged(type, newValue);
        }
    }

    public void setMaxInfusion(MachineInfusionEnum type, Integer value){
        if(type.isMaxValue()) {
            int newValue = 0;
            if (value <= 0) {
                setInfusionValue(type, 0);
            } else {
                setInfusionValue(type, value);
                newValue = value;
            }
            onInfusionChanged(type, newValue);
        }
    }

    public boolean isTypeActive(MachineInfusionEnum type){
        if(type.isMaxValue()){
            return getInfusionValue(type) > 0;
        }else if(type.isCurrentValue()){
            int maxValue = getMaxValueByType(type);
            return maxValue > 0;
        }
        return false;
    }

    public List<MachineInfusionEnum> getActiveInfusions(){
        List<MachineInfusionEnum> activeList = new ArrayList<>();
        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            if(type.isCurrentValue()) continue;
            if(getInfusionValue(type) <= 0) continue;
            activeList.add(type);
        }
        return activeList;
    }

    public int getMaxValueByCurrent(MachineInfusionEnum type){
        if(type.isCurrentValue()){
            return getMaxValueByCurrent(type);
        }
        return -1;
    }

    public int getCurrentValueByMax(MachineInfusionEnum type){
        if(type.isMaxValue()){
            return getCurrentValueByMaxType(type);
        }
        return -1;
    }

    public abstract void onInfusionChanged(MachineInfusionEnum type, int value);

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

package com.chamoisest.miningmadness.util.interfaces;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;

import java.util.EnumMap;

public interface IInfusionUtil {
    EnumMap<MachineInfusionEnum, Integer> infusionValues = new EnumMap<>(MachineInfusionEnum.class);
    default void setInfusionValue(MachineInfusionEnum type, Integer value){
        infusionValues.put(type, value);
    }

    default int getInfusionValue(MachineInfusionEnum type){
        if(infusionValues.containsKey(type)){
            return infusionValues.get(type);
        }
        return -1;
    }

    default String getInfusionNBTTag(MachineInfusionEnum type){
        return switch(type){
            case RANGE -> "range";
            case MAX_RANGE -> "max_range";
            case SPEED -> "speed";
            case MAX_SPEED -> "max_speed";
            case ENERGY_CAPACITY -> "energy_capacity";
            case MAX_ENERGY_CAPACITY -> "max_energy_capacity";
            case EFFICIENCY -> "efficiency";
            case MAX_EFFICIENCY -> "max_efficiency";
            case FORTUNE -> "fortune";
            case MAX_FORTUNE -> "max_fortune";
            case SILK_TOUCH -> "silk_touch";
            case MAX_SILK_TOUCH -> "max_silk_touch";
        };
    }

    default int getMaxValueByType(MachineInfusionEnum type){
        return switch(type){
            case RANGE -> getInfusionValue(MachineInfusionEnum.MAX_RANGE);
            case SPEED -> getInfusionValue(MachineInfusionEnum.MAX_SPEED);
            case ENERGY_CAPACITY -> getInfusionValue(MachineInfusionEnum.MAX_ENERGY_CAPACITY);
            case EFFICIENCY -> getInfusionValue(MachineInfusionEnum.MAX_EFFICIENCY);
            case FORTUNE -> getInfusionValue(MachineInfusionEnum.MAX_FORTUNE);
            case SILK_TOUCH -> getInfusionValue(MachineInfusionEnum.MAX_SILK_TOUCH);
            default -> -1;
        };
    }

    default int getCurrentValueByMaxType(MachineInfusionEnum type){
        return switch(type){
            case MAX_RANGE -> getInfusionValue(MachineInfusionEnum.RANGE);
            case MAX_SPEED -> getInfusionValue(MachineInfusionEnum.SPEED);
            case MAX_ENERGY_CAPACITY -> getInfusionValue(MachineInfusionEnum.ENERGY_CAPACITY);
            case MAX_EFFICIENCY -> getInfusionValue(MachineInfusionEnum.EFFICIENCY);
            case MAX_FORTUNE -> getInfusionValue(MachineInfusionEnum.FORTUNE);
            case MAX_SILK_TOUCH -> getInfusionValue(MachineInfusionEnum.SILK_TOUCH);
            default -> -1;
        };
    }

    default boolean isCurrentValue(MachineInfusionEnum type){
        return switch (type){
            case RANGE, SPEED, ENERGY_CAPACITY, EFFICIENCY, FORTUNE, SILK_TOUCH -> true;
            default -> false;
        };
    }

    default boolean isMaxValue(MachineInfusionEnum type){
        return switch (type){
            case MAX_RANGE, MAX_SPEED, MAX_ENERGY_CAPACITY, MAX_EFFICIENCY, MAX_FORTUNE, MAX_SILK_TOUCH -> true;
            default -> false;
        };
    }

    default EnumMap<MachineInfusionEnum, Integer> getActiveInfusionMap(){
        return infusionValues;
    }
}

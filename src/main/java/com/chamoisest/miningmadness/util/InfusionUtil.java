package com.chamoisest.miningmadness.util;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;

public class InfusionUtil {
    protected int range = 0;
    protected int maxRange = 0;

    protected int speed = 0;
    protected int maxSpeed = 0;

    protected int energyCapacity = 0;
    protected int maxEnergyCapacity = 0;

    protected int efficiency = 0;
    protected int maxEfficiency = 0;

    protected int fortune = 0;
    protected int maxFortune = 0;

    protected int silkTouch = 0;
    protected int maxSilkTouch = 0;

    protected void setInfusionValue(MachineInfusionEnum type, Integer value){
        switch(type){
            case RANGE -> range = value;
            case MAX_RANGE -> maxRange = value;
            case SPEED -> speed = value;
            case MAX_SPEED -> maxSpeed = value;
            case ENERGY_CAPACITY -> energyCapacity = value;
            case MAX_ENERGY_CAPACITY -> maxEnergyCapacity = value;
            case EFFICIENCY -> efficiency = value;
            case MAX_EFFICIENCY -> maxEfficiency = value;
            case FORTUNE -> fortune = value;
            case MAX_FORTUNE -> maxFortune = value;
            case SILK_TOUCH -> silkTouch = value;
            case MAX_SILK_TOUCH -> maxSilkTouch = value;
        }
    }

    protected int getInfusionValue(MachineInfusionEnum type){
        return switch(type){
            case RANGE -> range;
            case MAX_RANGE -> maxRange;
            case SPEED -> speed;
            case MAX_SPEED -> maxSpeed;
            case ENERGY_CAPACITY -> energyCapacity;
            case MAX_ENERGY_CAPACITY -> maxEnergyCapacity;
            case EFFICIENCY -> efficiency;
            case MAX_EFFICIENCY -> maxEfficiency;
            case FORTUNE -> fortune;
            case MAX_FORTUNE -> maxFortune;
            case SILK_TOUCH -> silkTouch;
            case MAX_SILK_TOUCH -> maxSilkTouch;
        };
    }

    protected String getInfusionNBTTag(MachineInfusionEnum type){
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

    protected int getMaxValueByType(MachineInfusionEnum type){
        return switch(type){
            case RANGE -> maxRange;
            case SPEED -> maxSpeed;
            case ENERGY_CAPACITY -> maxEnergyCapacity;
            case EFFICIENCY -> maxEfficiency;
            case FORTUNE -> maxFortune;
            case SILK_TOUCH -> maxSilkTouch;
            default -> -1;
        };
    }

    protected int getCurrentValueByMaxType(MachineInfusionEnum type){
        return switch(type){
            case MAX_RANGE -> range;
            case MAX_SPEED -> speed;
            case MAX_ENERGY_CAPACITY -> energyCapacity;
            case MAX_EFFICIENCY -> efficiency;
            case MAX_FORTUNE -> fortune;
            case MAX_SILK_TOUCH -> silkTouch;
            default -> -1;
        };
    }

    protected boolean isCurrentValue(MachineInfusionEnum type){
        return switch (type){
            case RANGE, SPEED, ENERGY_CAPACITY, EFFICIENCY, FORTUNE, SILK_TOUCH -> true;
            default -> false;
        };
    }

    protected boolean isMaxValue(MachineInfusionEnum type){
        return switch (type){
            case MAX_RANGE, MAX_SPEED, MAX_ENERGY_CAPACITY, MAX_EFFICIENCY, MAX_FORTUNE, MAX_SILK_TOUCH -> true;
            default -> false;
        };
    }
}

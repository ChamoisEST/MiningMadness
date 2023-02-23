package com.chamoisest.miningmadness.data.helpers;

import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;

import java.security.InvalidKeyException;

public class MachineUpgradeEnumHelper {
    public static MachineUpgradeEnum getEnum(String jsonValue) throws InvalidKeyException {
        return switch (jsonValue){
            case "fortune" -> MachineUpgradeEnum.FORTUNE;
            case "speed" -> MachineUpgradeEnum.SPEED;
            case "efficiency" -> MachineUpgradeEnum.EFFICIENCY;
            case "silk_touch" -> MachineUpgradeEnum.SILK_TOUCH;
            case "range" -> MachineUpgradeEnum.RANGE;
            case "energy_capacity" -> MachineUpgradeEnum.ENERGY_CAPACITY;
            default -> throw new InvalidKeyException("Invalid JSON key in MachineUpgradeEnum: '" + jsonValue + "'");
        };
    }
}

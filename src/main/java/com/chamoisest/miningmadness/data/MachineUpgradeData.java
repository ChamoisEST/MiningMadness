package com.chamoisest.miningmadness.data;

import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class MachineUpgradeData {

    public static Map<Item, Map<MachineUpgradeEnum, Integer>> machineBaseStats = new HashMap<>();
    public static Map<MachineUpgradeEnum, Integer> basicQuarryBaseStats = new HashMap<>();
    public static Map<MachineUpgradeEnum, Integer> advancedQuarryBaseStats = new HashMap<>();

    public static Map<MachineUpgradeEnum, Integer> machineInfusingStationBaseStats = new HashMap<>();

    public MachineUpgradeData(){

    }

    public static void fillStats(){
        basicQuarryBaseStats.put(MachineUpgradeEnum.FORTUNE, 360);
        basicQuarryBaseStats.put(MachineUpgradeEnum.SPEED, 360);
        basicQuarryBaseStats.put(MachineUpgradeEnum.EFFICIENCY, 360);
        basicQuarryBaseStats.put(MachineUpgradeEnum.SILK_TOUCH, 1);
        basicQuarryBaseStats.put(MachineUpgradeEnum.RANGE, 45);
        basicQuarryBaseStats.put(MachineUpgradeEnum.ENERGY_CAPACITY, 100);

        advancedQuarryBaseStats.put(MachineUpgradeEnum.FORTUNE, 950);
        advancedQuarryBaseStats.put(MachineUpgradeEnum.SPEED, 950);
        advancedQuarryBaseStats.put(MachineUpgradeEnum.EFFICIENCY, 950);
        advancedQuarryBaseStats.put(MachineUpgradeEnum.SILK_TOUCH, 1);
        advancedQuarryBaseStats.put(MachineUpgradeEnum.RANGE, 115);
        advancedQuarryBaseStats.put(MachineUpgradeEnum.ENERGY_CAPACITY, 250);

        machineInfusingStationBaseStats.put(MachineUpgradeEnum.SPEED, 360);
        machineInfusingStationBaseStats.put(MachineUpgradeEnum.EFFICIENCY, 360);
        machineInfusingStationBaseStats.put(MachineUpgradeEnum.ENERGY_CAPACITY, 100);

        machineBaseStats.put(MMBlocks.BASIC_QUARRY.get().asItem(), basicQuarryBaseStats);
        machineBaseStats.put(MMBlocks.ADVANCED_QUARRY.get().asItem(), advancedQuarryBaseStats);
        machineBaseStats.put(MMBlocks.MACHINE_INFUSING_STATION.get().asItem(), machineInfusingStationBaseStats);
    }

    public static int getMachineBaseStats(ItemStack item, MachineUpgradeEnum key) {
        return getMachineBaseStats(item.getItem(), key);
    }

    public static int getMachineBaseStats(Item item, MachineUpgradeEnum key){
        fillStats();

        Map<MachineUpgradeEnum, Integer> machineStats = machineBaseStats.get(item);
        if(machineStats == null) return 0;
        if(machineStats.get(key) != null){
            return machineStats.get(key);
        }
        return 0;
    }

    public static Set<MachineUpgradeEnum> getAllowedStats(ItemStack item) {
        return getAllowedStats(item.getItem());
    }

    public static Set<MachineUpgradeEnum> getAllowedStats(Item item){
        fillStats();

        Set<MachineUpgradeEnum> allowed = new HashSet<>();
        Map<MachineUpgradeEnum, Integer> machineStats = machineBaseStats.get(item);

        if(machineStats == null) return allowed;

        Iterator<Map.Entry<MachineUpgradeEnum, Integer>> iterator = machineStats.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<MachineUpgradeEnum, Integer> entry = iterator.next();
            allowed.add(entry.getKey());
        }

        return allowed;
    }
}

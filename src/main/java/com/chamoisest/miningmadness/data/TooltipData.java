package com.chamoisest.miningmadness.data;

import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class TooltipData {
    public Map<Integer, Component> tooltipList = new HashMap<>();

    public TooltipData(){
        tooltipList.put(0, Component.empty());
        tooltipList.put(1, Component.translatable("tooltip.miningmadness.not_enough_energy_error"));
        tooltipList.put(2, Component.translatable("tooltip.miningmadness.machine_output_full_error"));
        tooltipList.put(3, Component.translatable("tooltip.miningmadness.machine_running"));
        tooltipList.put(4, Component.translatable("tooltip.miningmadness.machine_stopped"));
        tooltipList.put(5, Component.translatable("tooltip.miningmadness.machine_runs_without_redstone"));
        tooltipList.put(6, Component.translatable("tooltip.miningmadness.machine_runs_with_redstone"));
        tooltipList.put(7, Component.translatable("tooltip.miningmadness.machine_igonres_redstone"));
        tooltipList.put(8, Component.translatable("tooltip.miningmadness.quarry_has_finished"));
        tooltipList.put(9, Component.translatable("tooltip.miningmadness.machine_can_run"));
        tooltipList.put(10, Component.translatable("tooltip.miningmadness.machine_cant_get_new_stats"));
        tooltipList.put(11, Component.translatable("tooltip.miningmadness.display_range_off"));
        tooltipList.put(12, Component.translatable("tooltip.miningmadness.display_range"));
    }

    public Component getToolTipComponent(int key){
        return tooltipList.get(key);
    }
}

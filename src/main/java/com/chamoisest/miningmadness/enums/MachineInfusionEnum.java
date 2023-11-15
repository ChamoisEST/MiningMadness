package com.chamoisest.miningmadness.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum MachineInfusionEnum {
    FORTUNE, MAX_FORTUNE,
    SPEED, MAX_SPEED,
    EFFICIENCY, MAX_EFFICIENCY,
    RANGE, MAX_RANGE,
    ENERGY_CAPACITY, MAX_ENERGY_CAPACITY,
    SILK_TOUCH, MAX_SILK_TOUCH;

    public MachineInfusionEnum getOpposite(){
        return switch (this){
            case FORTUNE -> MAX_FORTUNE;
            case SPEED -> MAX_SPEED;
            case EFFICIENCY -> MAX_EFFICIENCY;
            case RANGE -> MAX_RANGE;
            case ENERGY_CAPACITY -> MAX_ENERGY_CAPACITY;
            case SILK_TOUCH -> MAX_SILK_TOUCH;
            case MAX_FORTUNE -> FORTUNE;
            case MAX_SPEED -> SPEED;
            case MAX_EFFICIENCY -> EFFICIENCY;
            case MAX_RANGE -> RANGE;
            case MAX_ENERGY_CAPACITY -> ENERGY_CAPACITY;
            case MAX_SILK_TOUCH -> SILK_TOUCH;
        };
    }

    public MutableComponent getTooltipPrefix(){
        return switch (this) {
            case MAX_FORTUNE -> Component.translatable("tooltip.miningmadness.fortune").withStyle(MAX_FORTUNE.getStyle());
            case MAX_SPEED -> Component.translatable("tooltip.miningmadness.speed").withStyle(MAX_SPEED.getStyle());
            case MAX_EFFICIENCY -> Component.translatable("tooltip.miningmadness.efficiency").withStyle(MAX_EFFICIENCY.getStyle());
            case MAX_SILK_TOUCH -> Component.translatable("tooltip.miningmadness.silk_touch").withStyle(MAX_SILK_TOUCH.getStyle());
            case MAX_RANGE -> Component.translatable("tooltip.miningmadness.range").withStyle(MAX_RANGE.getStyle());
            case MAX_ENERGY_CAPACITY -> Component.translatable("tooltip.miningmadness.energy_capacity").withStyle(MAX_ENERGY_CAPACITY.getStyle());
            default -> Component.empty();
        };
    }

    public ChatFormatting getStyle(){
        return switch (this) {
            case MAX_FORTUNE -> ChatFormatting.BLUE;
            case MAX_SPEED -> ChatFormatting.YELLOW;
            case MAX_EFFICIENCY -> ChatFormatting.GOLD;
            case MAX_SILK_TOUCH -> ChatFormatting.LIGHT_PURPLE;
            case MAX_RANGE -> ChatFormatting.AQUA;
            case MAX_ENERGY_CAPACITY -> ChatFormatting.RED;
            default -> ChatFormatting.WHITE;
        };
    }

    public int getColor1() {
        return switch (this) {
            case MAX_FORTUNE -> 0xff0208b5;
            case MAX_SPEED -> 0xfffcef38;
            case MAX_EFFICIENCY -> 0xffffac30;
            case MAX_SILK_TOUCH -> 0xfff7a3f7;
            case MAX_RANGE -> 0xff2dfaf6;
            case MAX_ENERGY_CAPACITY -> 0xffb51500;
            default -> 0;
        };
    }

    public int getColor2() {
        return switch (this) {
            case MAX_FORTUNE -> 0xff00035c;
            case MAX_SPEED -> 0xffdbcf25;
            case MAX_EFFICIENCY -> 0xffcf881f;
            case MAX_SILK_TOUCH -> 0xffd485d4;
            case MAX_RANGE -> 0xff1fcfcb;
            case MAX_ENERGY_CAPACITY -> 0xff600b00;
            default -> 0;
        };
    }

    public boolean isCurrentValue(){
        return switch (this){
            case RANGE, SPEED, ENERGY_CAPACITY, EFFICIENCY, FORTUNE, SILK_TOUCH -> true;
            default -> false;
        };
    }

    public boolean isMaxValue(){
        return switch (this){
            case MAX_RANGE, MAX_SPEED, MAX_ENERGY_CAPACITY, MAX_EFFICIENCY, MAX_FORTUNE, MAX_SILK_TOUCH -> true;
            default -> false;
        };
    }

    public String getInfusionNBTTag(){
        return switch(this){
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
}

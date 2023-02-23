package com.chamoisest.miningmadness.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.security.InvalidKeyException;

public enum MachineUpgradeEnum {
    FORTUNE, SPEED, EFFICIENCY, SILK_TOUCH, RANGE, ENERGY_CAPACITY;

    public int getColor1() {
        return switch (this) {
            case FORTUNE -> 0xff0208b5;
            case SPEED -> 0xfffcef38;
            case EFFICIENCY -> 0xffffac30;
            case SILK_TOUCH -> 0xfff7a3f7;
            case RANGE -> 0xff2dfaf6;
            case ENERGY_CAPACITY -> 0xffb51500;
            default -> 0;
        };
    }

    public int getColor2() {
        return switch (this) {
            case FORTUNE -> 0xff00035c;
            case SPEED -> 0xffdbcf25;
            case EFFICIENCY -> 0xffcf881f;
            case SILK_TOUCH -> 0xffd485d4;
            case RANGE -> 0xff1fcfcb;
            case ENERGY_CAPACITY -> 0xff600b00;
            default -> 0;
        };
    }

    public MutableComponent getTooltipPrefix(){
        return switch (this) {
            case FORTUNE -> Component.translatable("tooltip.miningmadness.fortune").withStyle(FORTUNE.getStyle());
            case SPEED -> Component.translatable("tooltip.miningmadness.speed").withStyle(SPEED.getStyle());
            case EFFICIENCY -> Component.translatable("tooltip.miningmadness.efficiency").withStyle(EFFICIENCY.getStyle());
            case SILK_TOUCH -> Component.translatable("tooltip.miningmadness.silk_touch").withStyle(SILK_TOUCH.getStyle());
            case RANGE -> Component.translatable("tooltip.miningmadness.range").withStyle(RANGE.getStyle());
            case ENERGY_CAPACITY -> Component.translatable("tooltip.miningmadness.energy_capacity").withStyle(ENERGY_CAPACITY.getStyle());
            default -> Component.empty();
        };
    }

    public String getNBTName(){
        return switch (this) {
            case FORTUNE -> "fortune_infused";
            case SPEED -> "speed_infused";
            case EFFICIENCY -> "efficiency_infused";
            case SILK_TOUCH -> "silk_touch_infused";
            case RANGE -> "range_infused";
            case ENERGY_CAPACITY -> "energy_capacity_infused";
            default -> "";
        };
    }

    public ChatFormatting getStyle(){
        return switch (this) {
            case FORTUNE -> ChatFormatting.BLUE;
            case SPEED -> ChatFormatting.YELLOW;
            case EFFICIENCY -> ChatFormatting.GOLD;
            case SILK_TOUCH -> ChatFormatting.LIGHT_PURPLE;
            case RANGE -> ChatFormatting.AQUA;
            case ENERGY_CAPACITY -> ChatFormatting.RED;
            default -> ChatFormatting.WHITE;
        };
    }
}


package com.chamoisest.miningmadness.enums;

public enum TextureEnum {

    MACHINE_ON, MACHINE_OFF, MACHINE_ERROR, REDSTONE_ON, REDSTONE_OFF, REDSTONE_IGNORE, INFO_WIDGET,
    RANGE_ON, RANGE_OFF, SIDE_CONFIG, PLUS_ONE, PLUS_TEN, MINUS_ONE, MINUS_TEN;

    public int getX(){
        return switch (this) {
            case MACHINE_ON -> 128;
            case MACHINE_OFF -> 144;
            case MACHINE_ERROR -> 48;
            case REDSTONE_ON -> 16;
            case REDSTONE_OFF -> 0;
            case REDSTONE_IGNORE -> 32;
            case INFO_WIDGET -> 112;
            case RANGE_ON -> 96;
            case RANGE_OFF -> 80;
            case SIDE_CONFIG -> 64;
            case PLUS_ONE -> 160;
            case PLUS_TEN -> 192;
            case MINUS_ONE -> 176;
            case MINUS_TEN -> 208;
        };

    }

    public int getY(){
        return switch (this) {
            case MACHINE_ON -> 0;
            case MACHINE_OFF -> 0;
            case MACHINE_ERROR -> 0;
            case REDSTONE_ON -> 0;
            case REDSTONE_OFF -> 0;
            case REDSTONE_IGNORE -> 0;
            case INFO_WIDGET -> 0;
            case RANGE_ON -> 0;
            case RANGE_OFF -> 0;
            case SIDE_CONFIG -> 0;
            case PLUS_ONE -> 0;
            case PLUS_TEN -> 0;
            case MINUS_ONE -> 0;
            case MINUS_TEN -> 0;
        };

    }

}

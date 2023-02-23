package com.chamoisest.miningmadness.data;

import com.chamoisest.miningmadness.enums.TextureEnum;
import com.chamoisest.miningmadness.enums.TypeEnum;

import java.util.HashMap;
import java.util.Map;

public class TextureData {
    public Map<Integer, TextureEnum> running = new HashMap<>();
    public Map<Integer, TextureEnum> redstone = new HashMap<>();
    public Map<Integer, TextureEnum> range = new HashMap<>();


    public Map<TypeEnum, Map<Integer, TextureEnum>> textureData = new HashMap<>();

    public TextureData(){
        running.put(0, TextureEnum.MACHINE_OFF);
        running.put(1, TextureEnum.MACHINE_ON);
        running.put(2, TextureEnum.MACHINE_ERROR);
        redstone.put(0, TextureEnum.REDSTONE_OFF);
        redstone.put(1, TextureEnum.REDSTONE_ON);
        redstone.put(2, TextureEnum.REDSTONE_IGNORE);
        range.put(0, TextureEnum.RANGE_OFF);
        range.put(1, TextureEnum.RANGE_ON);

        textureData.put(TypeEnum.MACHINE, running);
        textureData.put(TypeEnum.REDSTONE, redstone);
        textureData.put(TypeEnum.RANGE, range);

    }

    public TextureEnum getTextureData(TypeEnum type, int key){
        Map<Integer, TextureEnum> textureData = this.textureData.get(type);
        if(textureData == null) return TextureEnum.MACHINE_ERROR;
        if(textureData.get(key) != null){
            return textureData.get(key);
        }
        return TextureEnum.MACHINE_ERROR;
    }
}

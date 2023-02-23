package com.chamoisest.miningmadness.client.screen.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */

public class EnergyInfoArea extends InfoArea{
    //private final IEnergyStorage energy;

    private final int energy;
    private final int maxEnergy;

    public EnergyInfoArea(int xMin, int yMin)  {
        this(xMin, yMin, 0, 0,14,64);
    }

    public EnergyInfoArea(int xMin, int yMin, int energy, int maxEnergy)  {
        this(xMin, yMin, energy, maxEnergy, 14, 64);
    }

    public EnergyInfoArea(int xMin, int yMin, int energy, int maxEnergy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
        this.maxEnergy = maxEnergy;
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(this.energy+"/"+this.maxEnergy+" FE"));
    }

    @Override
    public void draw(PoseStack transform) {
        final int height = area.getHeight();
        int stored = (int)(height*(this.energy/(float)this.maxEnergy));
        fillGradient(
                transform,
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}

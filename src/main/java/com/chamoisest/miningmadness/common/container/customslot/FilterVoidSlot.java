package com.chamoisest.miningmadness.common.container.customslot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FilterVoidSlot extends SlotItemHandler {

    protected boolean enabled = true;

    public FilterVoidSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isActive() {
        return enabled;
    }

    public FilterVoidSlot setEnabled(boolean enabled){
        this.enabled = enabled;
        return this;
    }


}

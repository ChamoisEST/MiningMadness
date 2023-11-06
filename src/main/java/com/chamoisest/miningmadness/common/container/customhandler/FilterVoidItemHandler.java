package com.chamoisest.miningmadness.common.container.customhandler;

import com.chamoisest.miningmadness.common.container.VoidFilterMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class FilterVoidItemHandler extends ItemStackHandler {

    public FilterVoidItemHandler(int slots) {
        super(slots);
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
    }


    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}

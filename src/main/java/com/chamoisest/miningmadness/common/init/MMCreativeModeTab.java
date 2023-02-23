package com.chamoisest.miningmadness.common.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MMCreativeModeTab {
    public static final CreativeModeTab MININGMADNESS_TAB = new CreativeModeTab("miningmadnesstab") {
        @Override
        public ItemStack makeIcon() {
            return ItemStack.EMPTY;
        }
    };
}

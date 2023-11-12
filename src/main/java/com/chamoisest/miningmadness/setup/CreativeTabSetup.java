package com.chamoisest.miningmadness.setup;

import com.chamoisest.miningmadness.MiningMadness;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeTabSetup {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MiningMadness.MODID);

    public static final List<Supplier<? extends ItemLike>> MININGMADNESS_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> MININGMADNESS_TAB = TABS.register("miningmadness_tab",
        () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.miningmadness_tab"))
                .icon(ItemSetup.LAPIS_INFUSED_NETHERITE_INGOT.get()::getDefaultInstance)
                .displayItems((displayParameters, output) -> {
                    MININGMADNESS_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()));
                })
                .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        MININGMADNESS_TAB_ITEMS.add(itemLike);
        return itemLike;
    }
}

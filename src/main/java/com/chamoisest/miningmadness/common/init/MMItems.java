package com.chamoisest.miningmadness.common.init;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.item.block.BaseMachineItem;
import com.chamoisest.miningmadness.common.item.block.RangeProjectorItem;
import com.chamoisest.miningmadness.common.item.block.VoidFilterItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiningMadness.MOD_ID);

    public static final RegistryObject<Item> BASIC_QUARRY_ITEM = MMItems.ITEMS.register("basic_quarry",
            () -> new BaseMachineItem(MMBlocks.BASIC_QUARRY.get(), new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ADVANCED_QUARRY_ITEM = MMItems.ITEMS.register("advanced_quarry",
            () -> new BaseMachineItem(MMBlocks.ADVANCED_QUARRY.get(), new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MACHINE_INFUSING_STATION_ITEM = MMItems.ITEMS.register("machine_infusing_station",
            () -> new BaseMachineItem(MMBlocks.MACHINE_INFUSING_STATION.get(), new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB).stacksTo(1)));

    public static final RegistryObject<Item> VOID_FILTER_ITEM = MMItems.ITEMS.register("void_filter",
            () -> new VoidFilterItem(MMBlocks.VOID_FILTER.get(), new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB)));

    public static final RegistryObject<Item> RANGE_PROJECTOR_ITEM = MMItems.ITEMS.register("range_projector",
            () -> new RangeProjectorItem(MMBlocks.RANGE_PROJECTOR.get(), new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB)));

    public static final RegistryObject<Item> LAPIS_INFUSED_NETHERITE_INGOT = MMItems.ITEMS.register("lapis_infused_netherite_ingot",
            () -> new Item(new Item.Properties().tab(MMCreativeModeTab.MININGMADNESS_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static class Tags {
        public static final TagKey<Item> MACHINE_INFUSABLE = create("infusion/infusable_machines");

        private static TagKey<Item> create(String location){
            return ItemTags.create(new ResourceLocation(MiningMadness.MOD_ID, location));
        }
    }
}

package com.chamoisest.miningmadness.common.init;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MMBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MiningMadness.MOD_ID);

    public static final RegistryObject<Block> BASIC_QUARRY = registerBlockWithoutItem("basic_quarry",
            () -> new BasicQuarryBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(0.5f)));

    public static final RegistryObject<Block> ADVANCED_QUARRY = registerBlockWithoutItem("advanced_quarry",
            () -> new AdvancedQuarryBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(0.5f)));

    public static final RegistryObject<Block> MACHINE_INFUSING_STATION = registerBlockWithoutItem("machine_infusing_station",
            () -> new MachineInfusingStationBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(0.5f).noOcclusion()));

    public static final RegistryObject<Block> VOID_FILTER = registerBlockWithoutItem("void_filter",
            () -> new VoidFilterBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(0.5f).noOcclusion()));

    public static final RegistryObject<Block> RANGE_PROJECTOR = registerBlockWithoutItem("range_projector",
            () -> new RangeProjectorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(0.5f).noOcclusion()));


    private static <T extends Block>RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return MMItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

package com.chamoisest.miningmadness.setup;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.blocks.quarry.BasicQuarry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockSetup {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MiningMadness.MODID);

    public static final RegistryObject<Block> BASIC_QUARRY = registerBlock("basic_quarry", () -> new BasicQuarry(
            BlockBehaviour.Properties.copy(Blocks.DISPENSER)
                    .strength(5.0f)
                    .pushReaction(PushReaction.DESTROY)
    ));

    private static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> supplier){
        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        registerBlockItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static RegistryObject<Block> registerBlockWithCustomItem(String name, Supplier<? extends Block> blockSupplier, Supplier<? extends BlockItem> itemSupplier){
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, itemSupplier);
        return block;
    }

    private static void registerBlockItem(String name, Supplier<? extends BlockItem> supplier) {
        CreativeTabSetup.addToTab(ItemSetup.ITEMS.register(name, supplier));
    }


}

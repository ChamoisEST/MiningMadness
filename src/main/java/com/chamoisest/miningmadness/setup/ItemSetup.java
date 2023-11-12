package com.chamoisest.miningmadness.setup;

import com.chamoisest.miningmadness.MiningMadness;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemSetup {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiningMadness.MODID);

    public static final RegistryObject<Item> LAPIS_INFUSED_NETHERITE_INGOT = registerItem("lapis_infused_netherite_ingot",
            () -> new Item(new Item.Properties())
    );

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier){
        return CreativeTabSetup.addToTab(ITEMS.register(name, supplier));
    }
}

package com.chamoisest.miningmadness.recipe;

import com.chamoisest.miningmadness.MiningMadness;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MiningMadness.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MachineInfusingStationRecipe>> MACHINE_INFUSING_SERIALIZER =
            SERIALIZERS.register("machine_infusing", () -> MachineInfusingStationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}

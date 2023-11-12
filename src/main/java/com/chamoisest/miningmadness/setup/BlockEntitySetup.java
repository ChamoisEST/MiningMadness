package com.chamoisest.miningmadness.setup;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.blockentities.quarry.BasicQuarryEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntitySetup {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MiningMadness.MODID);

    public static final RegistryObject<BlockEntityType<BasicQuarryEntity>> BASIC_QUARRY =
            BLOCK_ENTITIES.register("basic_quarry", () ->
                    BlockEntityType.Builder.of(BasicQuarryEntity::new, BlockSetup.BASIC_QUARRY.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}

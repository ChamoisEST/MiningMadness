package com.chamoisest.miningmadness.common.init;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MiningMadness.MOD_ID);

    public static final RegistryObject<BlockEntityType<BasicQuarryBlockEntity>> BASIC_QUARRY =
            BLOCK_ENTITIES.register("basic_quarry", () ->
                    BlockEntityType.Builder.of(BasicQuarryBlockEntity::new, MMBlocks.BASIC_QUARRY.get()).build(null));

    public static final RegistryObject<BlockEntityType<AdvancedQuarryBlockEntity>> ADVANCED_QUARRY =
            BLOCK_ENTITIES.register("advanced_quarry", () ->
                    BlockEntityType.Builder.of(AdvancedQuarryBlockEntity::new, MMBlocks.ADVANCED_QUARRY.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineInfusingStationBlockEntity>> MACHINE_INFUSING_STATION =
            BLOCK_ENTITIES.register("machine_infusing_station", () ->
                    BlockEntityType.Builder.of(MachineInfusingStationBlockEntity::new, MMBlocks.MACHINE_INFUSING_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<VoidFilterBlockEntity>> VOID_FILTER =
            BLOCK_ENTITIES.register("void_filter", () ->
                    BlockEntityType.Builder.of(VoidFilterBlockEntity::new, MMBlocks.VOID_FILTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<RangeProjectorBlockEntity>> RANGE_PROJECTOR =
            BLOCK_ENTITIES.register("range_projector", () ->
                    BlockEntityType.Builder.of(RangeProjectorBlockEntity::new, MMBlocks.RANGE_PROJECTOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}

package com.chamoisest.miningmadness;

import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapabilityProvider;
import com.chamoisest.miningmadness.setup.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MiningMadness.MODID)
@Mod.EventBusSubscriber(modid = MiningMadness.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MiningMadness {
    public static final String MODID = "miningmadness";
    public static Capability<InfusionCapability> INFUSION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public MiningMadness(){

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemSetup.ITEMS.register(bus);
        BlockSetup.BLOCKS.register(bus);
        CreativeTabSetup.TABS.register(bus);

        BlockEntitySetup.BLOCK_ENTITIES.register(bus);
        MenuTypeSetup.MENUS.register(bus);

    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<BlockEntity> event){
        if(event.getObject().getCapability(MiningMadness.INFUSION_CAPABILITY).isPresent()){
            System.out.println("Capability attached");
            event.addCapability(new ResourceLocation(MiningMadness.MODID, "infusion"), new InfusionCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(InfusionCapability.class);
    }
}

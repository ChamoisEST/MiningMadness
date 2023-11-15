package com.chamoisest.miningmadness.common.events;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.setup.MessagesSetup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MiningMadness.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            MessagesSetup.register();
        });
    }
}

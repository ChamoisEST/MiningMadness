package com.chamoisest.miningmadness.client.event;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.*;
import com.chamoisest.miningmadness.client.screen.renderer.block.RangeBER;
import com.chamoisest.miningmadness.common.block.entity.BasicQuarryBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import com.chamoisest.miningmadness.common.init.MMMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MiningMadness.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents(){

    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        MenuScreens.register(MMMenuTypes.BASIC_QUARRY_MENU.get(), BasicQuarryScreen::new);
        MenuScreens.register(MMMenuTypes.ADVANCED_QUARRY_MENU.get(), AdvancedQuarryScreen::new);
        MenuScreens.register(MMMenuTypes.MACHINE_INFUSING_STATION_MENU.get(), MachineInfusingStationScreen::new);
        MenuScreens.register(MMMenuTypes.VOID_FILTER_MENU.get(), VoidFilterScreen::new);
        MenuScreens.register(MMMenuTypes.RANGE_PROJECTOR_MENU.get(), RangeProjectorScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(MMBlockEntities.BASIC_QUARRY.get(), RangeBER<BasicQuarryBlockEntity>::new);
    }
}

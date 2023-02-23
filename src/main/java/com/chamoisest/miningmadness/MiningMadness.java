package com.chamoisest.miningmadness;

import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.common.init.MMItems;
import com.chamoisest.miningmadness.common.init.MMMenuTypes;
import com.chamoisest.miningmadness.networking.MMMessages;
import com.chamoisest.miningmadness.recipe.MMRecipes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MiningMadness.MOD_ID)
public class MiningMadness
{
    public static final String MOD_ID = "miningmadness";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MiningMadness()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MMBlocks.register(modEventBus);
        MMItems.register(modEventBus);

        MMBlockEntities.register(modEventBus);
        MMMenuTypes.register(modEventBus);

        MMRecipes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        MMMessages.register();
    }

}

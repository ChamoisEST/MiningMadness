package com.chamoisest.miningmadness.common.init;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.container.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MiningMadness.MOD_ID);

    public static final RegistryObject<MenuType<BasicQuarryMenu>> BASIC_QUARRY_MENU =
            registerMenuType(BasicQuarryMenu::new, "basic_quarry_menu");

    public static final RegistryObject<MenuType<AdvancedQuarryMenu>> ADVANCED_QUARRY_MENU =
            registerMenuType(AdvancedQuarryMenu::new, "advanced_quarry_menu");

    public static final RegistryObject<MenuType<MachineInfusingStationMenu>> MACHINE_INFUSING_STATION_MENU =
            registerMenuType(MachineInfusingStationMenu::new, "machine_infusing_station_menu");

    public static final RegistryObject<MenuType<VoidFilterMenu>> VOID_FILTER_MENU =
            registerMenuType(VoidFilterMenu::new, "void_filter_menu");

    public static final RegistryObject<MenuType<RangeProjectorMenu>> RANGE_PROJECTOR_MENU =
            registerMenuType(RangeProjectorMenu::new, "range_projector_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}

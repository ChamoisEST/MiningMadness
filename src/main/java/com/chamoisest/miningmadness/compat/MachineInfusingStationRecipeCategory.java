package com.chamoisest.miningmadness.compat;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.client.screen.elements.UpgradeBar;
import com.chamoisest.miningmadness.client.screen.renderer.EnergyInfoArea;
import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.common.item.block.BaseMachineItem;
import com.chamoisest.miningmadness.compat.base.BaseRecipeCategory;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.recipe.MachineInfusingStationRecipe;
import com.chamoisest.miningmadness.util.MouseUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MachineInfusingStationRecipeCategory extends BaseRecipeCategory<MachineInfusingStationRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(MiningMadness.MOD_ID, "machine_infusing");

    public MachineInfusingStationRecipeCategory(IGuiHelper helper){
        super(helper, "textures/gui/machine_infusing_station_gui.png");
    }

    @Override
    public void setBackgroundCoordinates() {
        this.backgroundStartX = 4;
        this.backgroundStartY = 4;
        this.backgroundWidth = 167;
        this.backgroundHeight = 91;
    }

    @Override
    public void setIngredient() {
        this.ingredient = new ItemStack(MMBlocks.MACHINE_INFUSING_STATION.get());
    }

    @Override
    public RecipeType<MachineInfusingStationRecipe> getRecipeType() {
        return JEIMiningMadnessPlugin.INFUSION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.miningmadness.machine_infusing_station");
    }

    @Override
    public void draw(MachineInfusingStationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {

        ItemStack currentlyDisplayedItemStack = getDisplayedItemStack(recipeSlotsView);

        Map<MachineUpgradeEnum, Integer> outputStats = recipe.getResultMachineStats();
        Iterator<Map.Entry<MachineUpgradeEnum, Integer>> iterator = outputStats.entrySet().iterator();
        int i = 0;
        while(iterator.hasNext()){
            Map.Entry<MachineUpgradeEnum, Integer> entry = iterator.next();

            int machineBaseStat = MachineUpgradeData.getMachineBaseStats(currentlyDisplayedItemStack, entry.getKey());
            if(machineBaseStat != 0) {
                new UpgradeBar(119, 16 + 6 * i, entry.getValue(),
                        MachineUpgradeData.getMachineBaseStats(currentlyDisplayedItemStack, entry.getKey()), entry.getKey()).draw(stack);
                i++;
            }
        }

        new EnergyInfoArea( 153 - backgroundStartX, 17 - backgroundStartY, 100000, 100000).draw(stack);
        super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    public ItemStack getDisplayedItemStack(IRecipeSlotsView recipeSlotsView){
        Optional<IRecipeSlotView> itemSlotView = recipeSlotsView.findSlotByName("infusableItemSlot");
        if(itemSlotView.isPresent()){
            Optional<ItemStack> optionalStack = itemSlotView.get().getDisplayedItemStack();
            if(optionalStack.isPresent()) {
                return optionalStack.get();
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public List<Component> getTooltipStrings(MachineInfusingStationRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {

        List<Component> componentList = List.of();
        ItemStack currentlyDisplayedItemStack = getDisplayedItemStack(recipeSlotsView);

        Map<MachineUpgradeEnum, Integer> outputStats = recipe.getResultMachineStats();
        Iterator<Map.Entry<MachineUpgradeEnum, Integer>> iterator = outputStats.entrySet().iterator();

        int i = 0;
        while(iterator.hasNext()){
            Map.Entry<MachineUpgradeEnum, Integer> entry = iterator.next();
            int machineBaseStat = MachineUpgradeData.getMachineBaseStats(currentlyDisplayedItemStack, entry.getKey());
            if(machineBaseStat != 0) {
                if (MouseUtil.isMouseOver(mouseX, mouseY, 119, 16 + 6 * i, 23, 3)) {
                    componentList = List.of(
                            Component.literal(entry.getKey().getTooltipPrefix().getString() + ": +" + entry.getValue()),
                            Component.literal(Component.translatable("tooltip.miningmadness.max").getString() + ": " +
                                    MachineUpgradeData.getMachineBaseStats(currentlyDisplayedItemStack, entry.getKey())));
                }
                i++;
            }
        }

        return componentList;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MachineInfusingStationRecipe recipe, IFocusGroup focuses) {
        ItemStack focusedStack = ItemStack.EMPTY;
        Stream<IFocus<ItemStack>> stream = focuses.getItemStackFocuses();
        Optional<IFocus<ItemStack>> optional = stream.findFirst();
        if(optional.isPresent()){
            Optional<ItemStack> stack = optional.get().getTypedValue().getItemStack();
            if(stack.isPresent()) {
                focusedStack = stack.get();
            }
        }

        IRecipeSlotBuilder infusableItemSlot = builder.addSlot(RecipeIngredientRole.INPUT, 40, 37).addIngredients(recipe.getIngredients().get(0));
        infusableItemSlot.setSlotName("infusableItemSlot");

        builder.addSlot(RecipeIngredientRole.INPUT, 40, 12).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 65, 37).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 62).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 37).addIngredients(recipe.getIngredients().get(4));

        if(recipe.getResultItem() == ItemStack.EMPTY){
            if(focusedStack != ItemStack.EMPTY && focusedStack.getItem() instanceof BaseMachineItem){
                builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 62).addItemStack(focusedStack);
            }else {
                builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 62).addIngredients(recipe.getIngredients().get(0));
            }
        }else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 62).addItemStack(recipe.getResultItem());
        }
    }
}

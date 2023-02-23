package com.chamoisest.miningmadness.compat;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.recipe.MachineInfusingStationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIMiningMadnessPlugin implements IModPlugin {
    public static RecipeType<MachineInfusingStationRecipe> INFUSION_TYPE =
            new RecipeType<>(MachineInfusingStationRecipeCategory.UID, MachineInfusingStationRecipe.class);
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MiningMadness.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                MachineInfusingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<MachineInfusingStationRecipe> recipesInfusing = rm.getAllRecipesFor(MachineInfusingStationRecipe.Type.INSTANCE);
        registration.addRecipes(INFUSION_TYPE, recipesInfusing);
    }


}

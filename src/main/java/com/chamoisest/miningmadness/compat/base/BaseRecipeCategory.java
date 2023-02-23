package com.chamoisest.miningmadness.compat.base;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.recipe.MachineInfusingStationRecipe;
import com.chamoisest.miningmadness.util.MouseUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class BaseRecipeCategory<RECIPE> implements IRecipeCategory<RECIPE> {
    public final ResourceLocation TEXTURE;

    private final IDrawable background;
    private final IDrawable icon;

    public int backgroundWidth;
    public int backgroundHeight;
    public int backgroundStartX;
    public int backgroundStartY;

    public ItemStack ingredient;


    protected BaseRecipeCategory(IGuiHelper helper, String texture) {
        this.TEXTURE = new ResourceLocation(MiningMadness.MOD_ID, texture);
        setBackgroundCoordinates();
        setIngredient();
        this.background = helper.createDrawable(TEXTURE, backgroundStartX, backgroundStartY, backgroundWidth, backgroundHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ingredient);
    }

    protected boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public abstract void setBackgroundCoordinates();
    public abstract void setIngredient();

    public abstract Component getTitle();

    public abstract RecipeType<RECIPE> getRecipeType();

    public abstract void setRecipe(IRecipeLayoutBuilder builder, MachineInfusingStationRecipe recipe, IFocusGroup focuses);
}

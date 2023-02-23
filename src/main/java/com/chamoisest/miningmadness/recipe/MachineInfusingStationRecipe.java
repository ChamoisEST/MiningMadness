package com.chamoisest.miningmadness.recipe;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.data.helpers.MachineUpgradeEnumHelper;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidKeyException;
import java.util.*;

public class MachineInfusingStationRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final Map<MachineUpgradeEnum, Integer> outputMachineStats;
    private final int energy;

    public MachineInfusingStationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, Map<MachineUpgradeEnum, Integer> outputMachineStats, int energy){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.outputMachineStats = outputMachineStats;
        this.energy = energy;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        boolean hasMainItem = false;
        int matches = 0;
        int neededMatches = recipeItems.size() - 1;
        Set<Integer> usedItems = new HashSet<>();

        for(int i = 0; i < recipeItems.size(); i++){
            if(recipeItems.get(i).test(ItemStack.EMPTY)){
                neededMatches--;
                continue;
            }

            if(i == 0){
                if(recipeItems.get(i).test(pContainer.getItem(0))){
                    hasMainItem = true;
                }
            }else{
                for(int l = 1; l < 5; l++){
                    if(usedItems.contains(l)) continue;
                    if(recipeItems.get(i).test(pContainer.getItem(l))){
                        usedItems.add(l);
                        matches++;
                    }
                }
            }
        }

        if(matches >= neededMatches && hasMainItem){
            return true;
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public Map<MachineUpgradeEnum, Integer> getResultMachineStats(){
        return this.outputMachineStats;
    }

    public int getRequiredEnergyForCraft(){
        return this.energy;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MachineInfusingStationRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "machine_infusing";
    }

    public static class Serializer implements RecipeSerializer<MachineInfusingStationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(MiningMadness.MOD_ID, "machine_infusing");

        @Override
        public MachineInfusingStationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            JsonObject outputObj = GsonHelper.getAsJsonObject(pSerializedRecipe, "output");
            ItemStack output;
            if(Objects.equals(outputObj.get("item").getAsString(), "")){
                output = ItemStack.EMPTY;
            }else {
                output = ShapedRecipe.itemStackFromJson(outputObj);
            }

            int energy = GsonHelper.getAsInt(pSerializedRecipe, "energy");

            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                JsonObject ingredient = ingredients.get(i).getAsJsonObject();

                String itemValue;
                JsonElement itemJson = ingredient.get("item");
                if(itemJson == null){
                    itemValue = ingredient.get("tag").getAsString();
                }else{
                    itemValue = itemJson.getAsString();
                }

                if(!Objects.equals(itemValue, "")) {
                    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                }
            }

            Map<MachineUpgradeEnum, Integer> outputMachineStats = new HashMap<>();
            JsonArray outputMachineStatsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "output_machine_stats");

            for(int l = 0; l < outputMachineStatsArray.size(); l++){
                JsonObject set = outputMachineStatsArray.get(l).getAsJsonObject();
                String type = GsonHelper.getAsString(set, "infuse_type");
                int amount = GsonHelper.getAsInt(set, "amount");

                try {
                    outputMachineStats.put(MachineUpgradeEnumHelper.getEnum(type), amount);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            }


            return new MachineInfusingStationRecipe(pRecipeId, output, inputs, outputMachineStats, energy);
        }

        @Override
        public @Nullable MachineInfusingStationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            int energy = buf.readInt();
            int machineUpgradeDataSize = buf.readInt();
            Map<MachineUpgradeEnum, Integer> outputMachineStats = new HashMap<>();

            for(int l = 0; l < machineUpgradeDataSize; l++){
                MachineUpgradeEnum e = buf.readEnum(MachineUpgradeEnum.class);
                int amount = buf.readInt();
                outputMachineStats.put(e, amount);
            }

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new MachineInfusingStationRecipe(id, output, inputs, outputMachineStats, energy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MachineInfusingStationRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            buf.writeInt(recipe.getRequiredEnergyForCraft());
            buf.writeInt(recipe.getResultMachineStats().size());
            for(Map.Entry<MachineUpgradeEnum, Integer> entry : recipe.getResultMachineStats().entrySet()){
                buf.writeEnum(entry.getKey());
                buf.writeInt(entry.getValue());
            }

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}

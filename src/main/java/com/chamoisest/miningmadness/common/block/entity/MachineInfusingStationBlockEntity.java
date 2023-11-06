package com.chamoisest.miningmadness.common.block.entity;

import com.chamoisest.miningmadness.common.container.MachineInfusingStationMenu;
import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import com.chamoisest.miningmadness.data.MachineUpgradeData;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.recipe.MachineInfusingStationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MachineInfusingStationBlockEntity extends BaseMachineBlockEntity {

    private int useItemsInSlotsFlags = 0;

    private NonNullList<Ingredient> currentRecipeItems;
    private Map<MachineUpgradeEnum, Integer> currentRecipeOutputStats;
    private Set<MachineUpgradeEnum> currentRecipeMachineBaseStats;
    private ItemStack currentRecipeOutputStack;
    private ItemStack currentRecipeInfusedStack;
    private SimpleContainer currentInventory;


    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> MachineInfusingStationBlockEntity.this.ENERGY_STORAGE.getEnergyStored();
                case 1 -> MachineInfusingStationBlockEntity.this.ENERGY_STORAGE.getMaxEnergyStored();
                case 2 -> MachineInfusingStationBlockEntity.this.getWorking();
                case 3 -> MachineInfusingStationBlockEntity.this.getStatusTooltip();
                case 4 -> MachineInfusingStationBlockEntity.this.getRedstone();
                case 5 -> MachineInfusingStationBlockEntity.this.getRedstoneTooltip();
                case 8 -> MachineInfusingStationBlockEntity.this.getInfused(MachineUpgradeEnum.SPEED);
                case 9 -> MachineInfusingStationBlockEntity.this.getInfused(MachineUpgradeEnum.ENERGY_CAPACITY);
                case 10 -> MachineInfusingStationBlockEntity.this.getInfused(MachineUpgradeEnum.EFFICIENCY);
                case 11 -> MachineInfusingStationBlockEntity.this.progress;
                case 12 -> MachineInfusingStationBlockEntity.this.maxProgress;
                case 13 -> MachineInfusingStationBlockEntity.this.useItemsInSlotsFlags;
                case 6 -> MachineInfusingStationBlockEntity.this.calculateEnergyReqPerOperation();
                case 7 -> MachineInfusingStationBlockEntity.this.calculateEnergyPerTick();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0, 1, 3, 5, 6, 7, 13 -> throw new UnsupportedOperationException("Key '" + index + "' has no setter!");
                case 2 -> updateWorking(value);
                case 4 -> updateRedstone(value);
                case 8 -> setInfused(MachineUpgradeEnum.SPEED, value);
                case 9 -> setInfused(MachineUpgradeEnum.ENERGY_CAPACITY, value);
                case 10 -> setInfused(MachineUpgradeEnum.EFFICIENCY, value);
                case 11 -> MachineInfusingStationBlockEntity.this.progress = value;
                case 12 -> MachineInfusingStationBlockEntity.this.maxProgress = value;
                default -> throw new UnsupportedOperationException("Key '" + index + "' not found!");
            }
            ;
        }

        @Override
        public int getCount() {
            return 14;
        }
    };
    public MachineInfusingStationBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, MMBlockEntities.MACHINE_INFUSING_STATION.get(), MachineInfusingStationMenu.SLOTS, 512, 120);
        this.displayNameTranslatable = "Machine Infusing Station";
        this.working = 1;
        this.statusTooltip = 9;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MachineInfusingStationMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MachineInfusingStationBlockEntity pEntity) {
        if(!level.isClientSide()) {
            pEntity.ENERGY_STORAGE.setMaxEnergyStored(pEntity.calculateEnergyCapacity());
            if(hasRecipe(pEntity)) setCurrentRecipeInfo(pEntity);
            else clearCurrentRecipeInfo(pEntity);

            if (!hasEnoughEnergy(pEntity)) {
                pEntity.updateWorking(2);
                setStatusTooltip(pEntity, 1);
                return;
            } else if(hasRecipe(pEntity) && !machineGetsNewStatsFromRecipe(pEntity)){
                pEntity.updateWorking(2);
                setStatusTooltip(pEntity, 10);
            }else if (hasError(pEntity)) {
                pEntity.updateWorking(1);
                setStatusTooltip(pEntity, 9);
            }

            if (isWorking(pEntity) && checkRedstone(level, pEntity) && hasRecipe(pEntity)) {
                extractEnergy(pEntity);
                setChanged(level, blockPos, blockState);
                infuseItem(pEntity, true);
                pEntity.progress++;
                if(pEntity.progress >= pEntity.maxProgress) {
                    infuseItem(pEntity, false);
                    resetProgress(pEntity);
                }

            }else{
                resetProgress(pEntity);
            }
        }
    }

    private static void setCurrentRecipeInfo(MachineInfusingStationBlockEntity pEntity){
        Level level = pEntity.level;

        pEntity.currentInventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            pEntity.currentInventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<MachineInfusingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MachineInfusingStationRecipe.Type.INSTANCE, pEntity.currentInventory, level);

        pEntity.currentRecipeItems = recipe.get().getIngredients();
        pEntity.currentRecipeInfusedStack = pEntity.itemHandler.getStackInSlot(0);
        pEntity.currentRecipeOutputStats = recipe.get().getResultMachineStats();

        if(recipe.get().getResultItem() == ItemStack.EMPTY){
            pEntity.currentRecipeOutputStack = pEntity.currentRecipeInfusedStack;
        }else{
            pEntity.currentRecipeOutputStack = new ItemStack(recipe.get().getResultItem().getItem(), pEntity.itemHandler.getStackInSlot(5).getCount() + 1);
        }

        pEntity.currentRecipeMachineBaseStats = MachineUpgradeData.getAllowedStats(pEntity.currentRecipeInfusedStack);


    }

    private static void clearCurrentRecipeInfo(MachineInfusingStationBlockEntity pEntity){

        pEntity.currentInventory = null;
        pEntity.currentRecipeItems = null;
        pEntity.currentRecipeOutputStats = null;
        pEntity.currentRecipeOutputStack = ItemStack.EMPTY;
        pEntity.currentRecipeInfusedStack = ItemStack.EMPTY;
        pEntity.currentRecipeMachineBaseStats = null;

    }

    private static void infuseItem(MachineInfusingStationBlockEntity pEntity, boolean simulate){
        Set<Integer> extractedSlots = new HashSet<>();
        Set<Ingredient> removedItems = new HashSet<>();

        if(!simulate){
            if(pEntity.currentRecipeMachineBaseStats.size() > 0) {
                boolean hasFortune = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(MachineUpgradeEnum.FORTUNE.getNBTName()) > 0;
                boolean hasSilkTouch = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(MachineUpgradeEnum.SILK_TOUCH.getNBTName()) > 0;

                for (Map.Entry<MachineUpgradeEnum, Integer> entry : pEntity.currentRecipeOutputStats.entrySet()) {
                    if(pEntity.currentRecipeMachineBaseStats.contains(entry.getKey())) {
                        int currentInfusedVal = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(entry.getKey().getNBTName());
                        int maxInfusedVal = MachineUpgradeData.getMachineBaseStats(pEntity.currentRecipeInfusedStack, entry.getKey());
                        int outputVal = Math.min(currentInfusedVal + entry.getValue(), maxInfusedVal);

                        if(entry.getKey() == MachineUpgradeEnum.FORTUNE && hasSilkTouch){
                            continue;
                        }else if(entry.getKey() == MachineUpgradeEnum.SILK_TOUCH && hasFortune){
                            continue;
                        }

                        pEntity.currentRecipeOutputStack.getOrCreateTag().putInt(entry.getKey().getNBTName(), outputVal);
                    }
                }
            }
        }

        for(int slot = 0; slot < 5; slot++){

            for (Ingredient ingredient : pEntity.currentRecipeItems) {
                if (removedItems.contains(ingredient)) continue;
                if (extractedSlots.contains(slot)) continue;

                if (ingredient.test(ItemStack.EMPTY)) {
                    removedItems.add(ingredient);
                } else if (ingredient.test(pEntity.itemHandler.getStackInSlot(slot))) {
                    removedItems.add(ingredient);
                    extractedSlots.add(slot);
                    if(simulate){
                        pEntity.useItemsInSlotsFlags |= (int)Math.pow(2, slot + 1);
                    }else {
                        pEntity.itemHandler.extractItem(slot, 1, false);
                    }
                }
            }
        }

        if(!simulate) {
            pEntity.itemHandler.setStackInSlot(5, pEntity.currentRecipeOutputStack);
        }
    }

    private static boolean machineGetsNewStatsFromRecipe(MachineInfusingStationBlockEntity pEntity){
        int maxedStats = 0;

        if(pEntity.currentRecipeMachineBaseStats.size() > 0) {
            boolean hasFortune = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(MachineUpgradeEnum.FORTUNE.getNBTName()) > 0;
            boolean hasSilkTouch = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(MachineUpgradeEnum.SILK_TOUCH.getNBTName()) > 0;
            for (Map.Entry<MachineUpgradeEnum, Integer> entry : pEntity.currentRecipeOutputStats.entrySet()) {
                if(pEntity.currentRecipeMachineBaseStats.contains(entry.getKey())) {
                    int currentInfusedVal = pEntity.currentRecipeInfusedStack.getOrCreateTag().getInt(entry.getKey().getNBTName());
                    int maxInfusedVal = MachineUpgradeData.getMachineBaseStats(pEntity.currentRecipeInfusedStack, entry.getKey());

                    if(entry.getKey() == MachineUpgradeEnum.FORTUNE && hasSilkTouch){
                        maxedStats++;
                        continue;
                    }else if(entry.getKey() == MachineUpgradeEnum.SILK_TOUCH && hasFortune){
                        maxedStats++;
                        continue;
                    }

                    if(currentInfusedVal >= maxInfusedVal){
                        maxedStats++;
                    }
                }
            }
        }

        if(maxedStats >= pEntity.currentRecipeOutputStats.size() && pEntity.currentRecipeOutputStats.size() != 0){
            return false;
        }

        return true;
    }

    private static void resetProgress(MachineInfusingStationBlockEntity entity){
        entity.progress = 0;
        entity.useItemsInSlotsFlags = 0;
    }

    private static boolean hasRecipe(MachineInfusingStationBlockEntity pEntity){
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<MachineInfusingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MachineInfusingStationRecipe.Type.INSTANCE, inventory, level);
        if(recipe.isPresent() && canOutputItem(inventory, recipe.get().getResultItem())) {
            pEntity.maxProgress = recipe.get().getRequiredEnergyForCraft() / pEntity.energy_req;
            return true;
        }
        return false;
    }

    private static boolean canOutputItem(SimpleContainer inventory, ItemStack stack){
        boolean canInsert = false;
        if(inventory.getItem(5).isEmpty()){
            canInsert = true;
        }else if(inventory.getItem(5).getItem() == stack.getItem()){
            if(inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount()){
                canInsert = true;
            }
        }
        return canInsert;
    }
}

package com.chamoisest.miningmadness.client.screen.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public abstract class BaseMenu<T extends BlockEntity> extends AbstractContainerMenu {

    public T blockEntity;
    protected Level level;
    protected ContainerData data;

    public BaseMenu(MenuType<?> type, int id, Inventory inv, T entity, ContainerData data, int containerSize){
        super(type, id);
        checkContainerSize(inv, containerSize);
        blockEntity = entity;
        this.level = inv.player.level;
        this.data = data;
    }

    public T getBlockEntity() {
        return this.blockEntity;
    }



    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return false;
    }

    protected void addPlayerInventorySlots(Inventory playerInventory, int pInvXOffset, int pInvYOffset, int pHotbarYOffset) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, pInvXOffset + l * 18, pInvYOffset + i * 18));
            }
        }

        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, pInvXOffset + m * 18, pHotbarYOffset));
        }
    }

    public int getEnergy() {
        return this.data.get(0);
    }

    public int getMaxEnergy(){
        return this.data.get(1);
    }

    public int getWorking(){
        return this.data.get(2);
    }

    public int getNewWorking(){
        int working = getWorking();
        return switch(working){
            case 0 -> 1;
            case 1 -> 0;
            default -> working;
        };
    }

    public int getNewRedstone(){
        int redstone = getRedstone();
        return switch(redstone){
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 0;
            default -> redstone;
        };
    }

    public int getStatusTooltip(){
        return this.data.get(3);
    }

    public int getRedstoneTooltip() {
        return this.data.get(5);
    }

    public int getRedstone(){
        return this.data.get(4);
    }

    public void setWorking(int value){
        this.data.set(2, value);
    }

    public void setRedstone(int value){
        this.data.set(4, value);
    }

    public int getEnergyPerTick() { return this.data.get(7); }
    public int getEnergyPerOperation() { return this.data.get(6); }

    public int getShowRangeTooltip() {
        return this.data.get(15);
    }

    public int getShowRange(){
        return this.data.get(14);
    }

    public void setShowRange(int value){
        this.data.set(14, value);
    }

    public int getNewShowRange(){
        int showRange = getShowRange();
        return switch(showRange){
            case 0 -> 1;
            case 1 -> 0;
            default -> showRange;
        };
    }

    public int getOperationFrequency(){
        return getEnergyPerOperation() / getEnergyPerTick();
    }
}

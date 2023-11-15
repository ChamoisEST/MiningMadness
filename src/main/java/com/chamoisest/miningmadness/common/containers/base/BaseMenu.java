package com.chamoisest.miningmadness.common.containers.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseMenu<T extends BlockEntity> extends AbstractContainerMenu {

    public T blockEntity;
    protected Level level;
    protected ContainerData data;

    protected BaseMenu(@Nullable MenuType<?> pMenuType, Inventory inv, T entity, ContainerData data, int pContainerId) {
        super(pMenuType, pContainerId);
        this.level = inv.player.level();
        this.blockEntity = entity;
        this.data = data;
    }

    @Override
    public abstract ItemStack quickMoveStack(Player pPlayer, int pIndex);

    @Override
    public abstract boolean stillValid(Player pPlayer);

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
}

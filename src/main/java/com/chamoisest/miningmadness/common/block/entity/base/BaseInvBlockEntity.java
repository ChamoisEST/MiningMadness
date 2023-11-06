package com.chamoisest.miningmadness.common.block.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseInvBlockEntity extends BlockEntity implements MenuProvider {
    protected ItemStackHandler itemHandler;

    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected String displayNameTranslatable;


    public BaseInvBlockEntity(BlockPos pos, BlockState state, BlockEntityType beType, int invSize, boolean hasCustomItemHandler) {
        super(beType, pos, state);
        if(!hasCustomItemHandler) {
            itemHandler = new ItemStackHandler(invSize) {
                @Override
                protected void onContentsChanged(int slot) {
                    BaseInvBlockEntity.this.onContentsChanged();
                }

                @Override
                public int getSlotLimit(int slot) {
                    return getNewSlotLimit();
                }

                @Override
                public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                    return isValid(slot, stack);
                }
            };
        }
    }

    public abstract void onContentsChanged();

    public abstract int getNewSlotLimit();

    public abstract boolean isValid(int slot, @NotNull ItemStack stack);



    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(displayNameTranslatable);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }


}

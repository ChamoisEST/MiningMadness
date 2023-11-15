package com.chamoisest.miningmadness.common.blockentities.base;

import com.chamoisest.miningmadness.common.blockentities.interfaces.ICustomMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

public abstract class BaseMenuEntity extends BlockEntity implements ICustomMenuProvider {
    private String displayName;

    private static final String ITEM_TAG = "inventory";

    public final ItemStackHandler itemHandler;
    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public BaseMenuEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int invSize) {
        super(pType, pPos, pBlockState);
        itemHandler = new ItemStackHandler(invSize){
            @Override
            protected void onContentsChanged(int slot) {
                BaseMenuEntity.this.onContentsChanged(slot);
            }

            @Override
            public int getSlotLimit(int slot) {
                return BaseMenuEntity.this.getSlotLimit(slot);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return BaseMenuEntity.this.isItemValid(slot, stack);
            }
        };
    }

    public abstract void onContentsChanged(int slot);
    public abstract int getSlotLimit(int slot);
    public abstract boolean isItemValid(int slot, @NotNull ItemStack stack);

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer);

    protected void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(displayName);
    }

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
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(ITEM_TAG, itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound(ITEM_TAG));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }
}

package com.chamoisest.miningmadness.common.block.entity;

import com.chamoisest.miningmadness.common.block.entity.base.BaseInvBlockEntity;
import com.chamoisest.miningmadness.common.container.VoidFilterMenu;
import com.chamoisest.miningmadness.common.container.customhandler.FilterVoidItemHandler;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoidFilterBlockEntity extends BaseInvBlockEntity implements MenuProvider {

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {

            }
        }

        @Override
        public int getCount() {
            return 0;
        }
    };

    public VoidFilterBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, MMBlockEntities.VOID_FILTER.get(), VoidFilterMenu.SLOTS, true);
        this.displayNameTranslatable = "Void Filter";

        itemHandler = new FilterVoidItemHandler(VoidFilterMenu.SLOTS) {};
    }

    @Override
    public void onContentsChanged() {
        setChanged();
    }

    @Override
    public int getNewSlotLimit() {
        return 1;
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("Void Filter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VoidFilterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, VoidFilterBlockEntity pEntity) {
        if (!level.isClientSide()) {


        }
    }
}

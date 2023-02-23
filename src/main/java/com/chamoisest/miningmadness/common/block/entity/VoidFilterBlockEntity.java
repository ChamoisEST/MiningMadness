package com.chamoisest.miningmadness.common.block.entity;

import com.chamoisest.miningmadness.client.screen.VoidFilterMenu;
import com.chamoisest.miningmadness.common.init.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VoidFilterBlockEntity extends BlockEntity implements MenuProvider {

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

    public VoidFilterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MMBlockEntities.VOID_FILTER.get(), pPos, pBlockState);
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
        if(!level.isClientSide()) {

        }
    }
}

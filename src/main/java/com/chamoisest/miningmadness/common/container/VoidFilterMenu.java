package com.chamoisest.miningmadness.common.container;

import com.chamoisest.miningmadness.client.screen.base.BaseMenu;
import com.chamoisest.miningmadness.common.block.entity.VoidFilterBlockEntity;
import com.chamoisest.miningmadness.common.container.customslot.FilterVoidSlot;
import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.common.init.MMMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class VoidFilterMenu extends BaseMenu<VoidFilterBlockEntity> {
    public static final int SLOTS = 9;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    public VoidFilterMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        this(id, inv, (VoidFilterBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(0));
    }

    public VoidFilterMenu(int id, Inventory inv, VoidFilterBlockEntity entity, ContainerData data){
        super(MMMenuTypes.VOID_FILTER_MENU.get(), id, inv, entity, data, SLOTS);

        addPlayerInventorySlots(inv, 8, 84, 142);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new FilterVoidSlot(handler, 0, 62, 17));
            this.addSlot(new FilterVoidSlot(handler, 1, 80, 17));
            this.addSlot(new FilterVoidSlot(handler, 2, 98, 17));
            this.addSlot(new FilterVoidSlot(handler, 3, 62, 35));
            this.addSlot(new FilterVoidSlot(handler, 4, 80, 35));
            this.addSlot(new FilterVoidSlot(handler, 5, 98, 35));
            this.addSlot(new FilterVoidSlot(handler, 6, 62, 53));
            this.addSlot(new FilterVoidSlot(handler, 7, 80, 53));
            this.addSlot(new FilterVoidSlot(handler, 8, 98, 53));
        });

        addDataSlots(data);

    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if(pClickType == ClickType.PICKUP){
            if(pSlotId > VANILLA_SLOT_COUNT - 1 && pSlotId < VANILLA_SLOT_COUNT + SLOTS - 1){
                Slot slot = slots.get(pSlotId);

                ItemStack carried = getCarried();

                if(!carried.isEmpty()){
                    boolean canAccept = true;
                    ItemStack stack = carried.copy();
                    stack.setCount(1);

                    for(int i = VANILLA_SLOT_COUNT - 1; i < VANILLA_SLOT_COUNT + SLOTS - 1; i++){
                        if(getSlot(i).getItem().sameItem(stack)) {
                            canAccept = false;
                        }
                    }
                    if(canAccept){
                        slot.set(stack);
                    }
                }else{
                    slot.set(ItemStack.EMPTY);
                }
                return;
            }
        }
        super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot slot = this.slots.get(index);
        int freeSlot = -1;

        if(slot.hasItem()){
            ItemStack currentStack = slot.getItem().copy();
            currentStack.setCount(1);
            if(index >= VANILLA_SLOT_COUNT){
                slot.set(ItemStack.EMPTY);
                return ItemStack.EMPTY;
            }else{
                for(int i = VANILLA_SLOT_COUNT - 1; i < (VANILLA_SLOT_COUNT + SLOTS); i++){
                    if(this.getSlot(i).getItem().sameItem(currentStack)){
                        return ItemStack.EMPTY;
                    }

                    if(this.getSlot(i).getItem().equals(ItemStack.EMPTY, false) && freeSlot == -1){
                        freeSlot = i;
                    }
                }

                if(freeSlot != -1){
                    this.slots.get(freeSlot).set(currentStack);
                }

                return currentStack;
            }
        }
        return ItemStack.EMPTY;

    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, MMBlocks.VOID_FILTER.get());
    }

}

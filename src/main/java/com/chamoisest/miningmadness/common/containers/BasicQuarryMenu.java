package com.chamoisest.miningmadness.common.containers;

import com.chamoisest.miningmadness.common.blockentities.quarry.BasicQuarryEntity;
import com.chamoisest.miningmadness.common.containers.base.BaseMenu;
import com.chamoisest.miningmadness.common.containers.interfaces.IMenuWithPlayerSlots;
import com.chamoisest.miningmadness.setup.BlockSetup;
import com.chamoisest.miningmadness.setup.MenuTypeSetup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class BasicQuarryMenu extends BaseMenu<BasicQuarryEntity> implements IMenuWithPlayerSlots {

    public static final int SLOTS = 12;
    public static final int DATA_SLOTS = 0;

    public BasicQuarryMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        this(inv, (BasicQuarryEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(DATA_SLOTS), id);
    }

    public BasicQuarryMenu(Inventory inv, BasicQuarryEntity entity, ContainerData data, int pContainerId) {
        super(MenuTypeSetup.BASIC_QUARRY_MENU.get(), inv, entity, data, pContainerId);

        addPlayerInventorySlots(inv, 8, 84, 142);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 16, 17));
            this.addSlot(new SlotItemHandler(handler, 1, 34, 17));
            this.addSlot(new SlotItemHandler(handler, 2, 52, 17));
            this.addSlot(new SlotItemHandler(handler, 3, 70, 17));
            this.addSlot(new SlotItemHandler(handler, 4, 16, 35));
            this.addSlot(new SlotItemHandler(handler, 5, 34, 35));
            this.addSlot(new SlotItemHandler(handler, 6, 52, 35));
            this.addSlot(new SlotItemHandler(handler, 7, 70, 35));
            this.addSlot(new SlotItemHandler(handler, 8, 16, 53));
            this.addSlot(new SlotItemHandler(handler, 9, 34, 53));
            this.addSlot(new SlotItemHandler(handler, 10, 52, 53));
            this.addSlot(new SlotItemHandler(handler, 11, 70, 53));
        });
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if(!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyStack = sourceStack.copy();

        if(pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + SLOTS, false)){
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + SLOTS){
            if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,  false)){
                return ItemStack.EMPTY;
            }
        } else{
            System.out.println("Invalid slotIndex: " + pIndex);
            return ItemStack.EMPTY;
        }

        if(sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(pPlayer, sourceStack);
        return copyStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, BlockSetup.BASIC_QUARRY.get());
    }
}

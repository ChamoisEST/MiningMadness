package com.chamoisest.miningmadness.common.container;

import com.chamoisest.miningmadness.client.screen.base.BaseMenu;
import com.chamoisest.miningmadness.common.block.entity.BasicQuarryBlockEntity;
import com.chamoisest.miningmadness.common.init.MMBlocks;
import com.chamoisest.miningmadness.common.init.MMMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BasicQuarryMenu extends BaseMenu<BasicQuarryBlockEntity> {

    public static final int SLOTS = 12;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    public BasicQuarryMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        this(id, inv, (BasicQuarryBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(16));
    }

    public BasicQuarryMenu(int id, Inventory inv, BasicQuarryBlockEntity entity, ContainerData data){
        super(MMMenuTypes.BASIC_QUARRY_MENU.get(), id, inv, entity, data, SLOTS);

        addPlayerInventorySlots(inv, 8, 84, 142);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 16, 17));
            this.addSlot(new SlotItemHandler(handler, 4, 16, 35));
            this.addSlot(new SlotItemHandler(handler, 8, 16, 53));
            this.addSlot(new SlotItemHandler(handler, 1, 34, 17));
            this.addSlot(new SlotItemHandler(handler, 5, 34, 35));
            this.addSlot(new SlotItemHandler(handler, 9, 34, 53));
            this.addSlot(new SlotItemHandler(handler, 2, 52, 17));
            this.addSlot(new SlotItemHandler(handler, 6, 52, 35));
            this.addSlot(new SlotItemHandler(handler, 10, 52, 53));
            this.addSlot(new SlotItemHandler(handler, 3, 70, 17));
            this.addSlot(new SlotItemHandler(handler, 7, 70, 35));
            this.addSlot(new SlotItemHandler(handler, 11, 70, 53));
        });

        addDataSlots(data);

    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = SLOTS;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, MMBlocks.BASIC_QUARRY.get());
    }

    public int getSpeedInfused(){
        return this.data.get(8);
    }

    public void setSpeedInfused(int value){
        this.data.set(8, value);
    }

    public int getEnergyCapacityInfused(){
        return this.data.get(9);
    }

    public void setEnergyCapacityInfused(int value){
        this.data.set(9, value);
    }

    public int getEfficiencyInfused(){
        return this.data.get(10);
    }

    public void setEfficiencyInfused(int value){
        this.data.set(10, value);
    }

    public int getFortuneInfused(){
        return this.data.get(11);
    }

    public void setFortuneInfused(int value){
        this.data.set(11, value);
    }

    public int getSilkTouchInfused(){
        return this.data.get(12);
    }

    public void setSilkTouchInfused(int value){
        this.data.set(12, value);
    }

    public int getRangeInfused(){
        return this.data.get(13);
    }

    public void setRangeInfused(int value){
        this.data.set(13, value);
    }
}

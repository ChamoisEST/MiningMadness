package com.chamoisest.miningmadness.networking.packet;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import com.chamoisest.miningmadness.common.container.customslot.FilterVoidSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GhostFilterVoidC2SPacket {
    private final int slotNr;
    private final ItemStack stack;

    public GhostFilterVoidC2SPacket(int slotNr, ItemStack stack){
        this.slotNr = slotNr;
        this.stack = stack;
    }

    public GhostFilterVoidC2SPacket(FriendlyByteBuf buf){
        this.slotNr = buf.readInt();
        this.stack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(slotNr);
        buf.writeItem(stack);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;

            Slot slot = player.containerMenu.getSlot(slotNr);
            stack.setCount(1);
            if(slot instanceof FilterVoidSlot){
                slot.set(stack);
            }
        });
    }
}

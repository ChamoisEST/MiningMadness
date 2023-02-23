package com.chamoisest.miningmadness.networking.packet;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RedstoneButtonSyncC2SPacket {
    private final int redstone;
    private final BlockPos pos;

    public RedstoneButtonSyncC2SPacket(int redstone, BlockPos pos){
        this.redstone = redstone;
        this.pos = pos;
    }

    public RedstoneButtonSyncC2SPacket(FriendlyByteBuf buf){
        this.redstone = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(redstone);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();
            if(level.getBlockEntity(pos) instanceof BaseMachineBlockEntity blockEntity) {
                blockEntity.updateRedstone(redstone);
            }
        });
    }
}

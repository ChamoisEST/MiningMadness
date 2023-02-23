package com.chamoisest.miningmadness.networking.packet;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RunningButtonSyncC2SPacket {
    private final int running;
    private final BlockPos pos;

    public RunningButtonSyncC2SPacket(int running, BlockPos pos){
        this.running = running;
        this.pos = pos;
    }

    public RunningButtonSyncC2SPacket(FriendlyByteBuf buf){
        this.running = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(running);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();
            if(level.getBlockEntity(pos) instanceof BaseMachineBlockEntity blockEntity) {
                blockEntity.updateWorking(running);
            }
        });
    }
}

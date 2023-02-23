package com.chamoisest.miningmadness.networking.packet;

import com.chamoisest.miningmadness.common.block.entity.base.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShowRangeButtonSyncC2SPacket {
    private final int showRange;
    private final BlockPos pos;

    public ShowRangeButtonSyncC2SPacket(int showRange, BlockPos pos){
        this.showRange = showRange;
        this.pos = pos;
    }

    public ShowRangeButtonSyncC2SPacket(FriendlyByteBuf buf){
        this.showRange = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(showRange);
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();
            if(level.getBlockEntity(pos) instanceof BaseMachineBlockEntity blockEntity) {
                blockEntity.updateShowRange(showRange);
            }
        });
    }
}

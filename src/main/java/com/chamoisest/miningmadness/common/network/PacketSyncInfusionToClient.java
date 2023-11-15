package com.chamoisest.miningmadness.common.network;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncInfusionToClient {

    public final BlockPos pos;
    public final MachineInfusionEnum type;
    public final int value;
    public PacketSyncInfusionToClient(MachineInfusionEnum type, int value, BlockPos pos){
        this.type = type;
        this.value = value;
        this.pos = pos;
    }

    public PacketSyncInfusionToClient(FriendlyByteBuf buf){
        this(buf.readEnum(MachineInfusionEnum.class), buf.readInt(), buf.readBlockPos());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeEnum(type);
        buf.writeInt(value);
        buf.writeBlockPos(pos);
    }

    public static void handle(PacketSyncInfusionToClient msg, Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleInfusionPacket(msg, supplier));
        });
        ctx.setPacketHandled(true);
    }
}

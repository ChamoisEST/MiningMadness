package com.chamoisest.miningmadness.common.network;

import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncEnergyToClient {
    public final BlockPos pos;
    public final int maxEnergy;
    public final int currentEnergy;
    public PacketSyncEnergyToClient(int currentEnergy, int maxEnergy, BlockPos pos){
        this.currentEnergy = currentEnergy;
        this.maxEnergy = maxEnergy;
        this.pos = pos;
    }

    public PacketSyncEnergyToClient(FriendlyByteBuf buf){
        this(buf.readInt(), buf.readInt(), buf.readBlockPos());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(currentEnergy);
        buf.writeInt(maxEnergy);
        buf.writeBlockPos(pos);
    }

    public static void handle(PacketSyncEnergyToClient msg, Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleEnergyPacket(msg, supplier));
        });
        ctx.setPacketHandled(true);
    }
}

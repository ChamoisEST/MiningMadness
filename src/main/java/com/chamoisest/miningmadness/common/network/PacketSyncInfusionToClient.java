package com.chamoisest.miningmadness.common.network;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.util.InfusionUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class PacketSyncInfusionToClient extends InfusionUtil {

    public PacketSyncInfusionToClient(Map<MachineInfusionEnum, Integer> updateData){
        for(Map.Entry<MachineInfusionEnum, Integer> entry : updateData.entrySet()){
            setInfusionValue(entry.getKey(), entry.getValue());
        }
    }

    public PacketSyncInfusionToClient(FriendlyByteBuf buf) {
        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            setInfusionValue(type, buf.readInt());
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        for(MachineInfusionEnum type : MachineInfusionEnum.values()){
            buf.writeInt(getInfusionValue(type));
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();

        });
        return true;
    }
}

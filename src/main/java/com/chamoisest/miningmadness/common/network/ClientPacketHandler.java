package com.chamoisest.miningmadness.common.network;

import com.chamoisest.miningmadness.common.blockentities.base.BaseEnergyEntity;
import com.chamoisest.miningmadness.common.blockentities.base.BaseInfusionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handleInfusionPacket(PacketSyncInfusionToClient message, Supplier<NetworkEvent.Context> supplier){

        Level level = Minecraft.getInstance().level;
        if(level != null && level.isLoaded(message.pos)){
            BlockEntity entity = level.getBlockEntity(message.pos);
            if(entity instanceof BaseInfusionEntity infusionEntity){
                infusionEntity.getInfusionCapability().setInfusionValue(message.type, message.value);
            }
        }
    }

    public static void handleEnergyPacket(PacketSyncEnergyToClient message, Supplier<NetworkEvent.Context> supplier){
        Level level = Minecraft.getInstance().level;

        if(level != null && level.isLoaded(message.pos)){
            BlockEntity entity = level.getBlockEntity(message.pos);
            if(entity instanceof BaseEnergyEntity energyEntity){
                energyEntity.setClientEnergy(message.currentEnergy);
                energyEntity.setClientMaxEnergy(message.maxEnergy);
            }
        }
    }
}

package com.chamoisest.miningmadness.setup;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.network.PacketSyncEnergyToClient;
import com.chamoisest.miningmadness.common.network.PacketSyncInfusionToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MessagesSetup {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MiningMadness.MODID, "main"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(PacketSyncInfusionToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncInfusionToClient::new)
                .encoder(PacketSyncInfusionToClient::toBytes)
                .consumerMainThread(PacketSyncInfusionToClient::handle)
                .add();

        INSTANCE.messageBuilder(PacketSyncEnergyToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncEnergyToClient::new)
                .encoder(PacketSyncEnergyToClient::toBytes)
                .consumerMainThread(PacketSyncEnergyToClient::handle)
                .add();
    }

    public static void sendToServer(Object message) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }

    public static void sendToPlayer(Object message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void serverToClients(Object message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendToPlayersInChunk(Object message, LevelChunk chunk){
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
    }
}

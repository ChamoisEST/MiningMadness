package com.chamoisest.miningmadness.networking;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.networking.packet.GhostFilterVoidC2SPacket;
import com.chamoisest.miningmadness.networking.packet.RedstoneButtonSyncC2SPacket;
import com.chamoisest.miningmadness.networking.packet.RunningButtonSyncC2SPacket;
import com.chamoisest.miningmadness.networking.packet.ShowRangeButtonSyncC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MMMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MiningMadness.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(RedstoneButtonSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RedstoneButtonSyncC2SPacket::new)
                .encoder(RedstoneButtonSyncC2SPacket::toBytes)
                .consumerMainThread(RedstoneButtonSyncC2SPacket::handle)
                .add();

        net.messageBuilder(RunningButtonSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RunningButtonSyncC2SPacket::new)
                .encoder(RunningButtonSyncC2SPacket::toBytes)
                .consumerMainThread(RunningButtonSyncC2SPacket::handle)
                .add();

        net.messageBuilder(ShowRangeButtonSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ShowRangeButtonSyncC2SPacket::new)
                .encoder(ShowRangeButtonSyncC2SPacket::toBytes)
                .consumerMainThread(ShowRangeButtonSyncC2SPacket::handle)
                .add();

        net.messageBuilder(GhostFilterVoidC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GhostFilterVoidC2SPacket::new)
                .encoder(GhostFilterVoidC2SPacket::toBytes)
                .consumerMainThread(GhostFilterVoidC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}

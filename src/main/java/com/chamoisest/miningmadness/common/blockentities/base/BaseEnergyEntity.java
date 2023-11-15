package com.chamoisest.miningmadness.common.blockentities.base;

import com.chamoisest.miningmadness.common.network.PacketSyncEnergyToClient;
import com.chamoisest.miningmadness.setup.MessagesSetup;
import com.chamoisest.miningmadness.util.MMEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseEnergyEntity extends BaseMenuEntity{

    private static final String ENERGY_TAG = "energy";

    protected final MMEnergyStorage energyHandler;

    protected LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public BaseEnergyEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int invSize) {
        super(pType, pPos, pBlockState, invSize);
        energyHandler = new MMEnergyStorage(getCapacity(), getMaxTransfer()) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                sendUpdateEnergyPacket();
            }
        };
    }

    public abstract int getCapacity();
    public abstract int getMaxTransfer();

    public void setClientEnergy(int energy){
        if(level != null && level.isClientSide()){
            energyHandler.setEnergy(energy);
        }
    }

    public void setClientMaxEnergy(int maxEnergy){
        if(level != null && level.isClientSide()){
            energyHandler.setMaxEnergy(maxEnergy);
        }
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(this::getEnergyHandler);
        sendUpdateEnergyPacket();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt(ENERGY_TAG, getEnergyHandler().getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        energyHandler.setEnergy(pTag.getInt(ENERGY_TAG));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(ENERGY_TAG, getEnergyHandler().getEnergyStored());
        sendUpdateEnergyPacket();
        return tag;
    }

    public void sendUpdateEnergyPacket(){
        if(this.level == null) return;
        if(!level.isClientSide()) {
            BlockPos pos = this.getBlockPos();
            LevelChunk chunk = this.level.getChunkAt(pos);
            MessagesSetup.sendToPlayersInChunk(new PacketSyncEnergyToClient(getEnergyHandler().getEnergyStored(), getEnergyHandler().getMaxEnergyStored(), pos), chunk);
        }
    }
}

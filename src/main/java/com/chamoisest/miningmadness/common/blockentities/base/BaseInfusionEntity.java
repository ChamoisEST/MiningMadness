package com.chamoisest.miningmadness.common.blockentities.base;

import com.chamoisest.miningmadness.MiningMadness;
import com.chamoisest.miningmadness.common.blockentities.interfaces.IInfusable;
import com.chamoisest.miningmadness.common.capabilities.infusion.IInfusionCapability;
import com.chamoisest.miningmadness.common.capabilities.infusion.InfusionCapability;
import com.chamoisest.miningmadness.common.network.PacketSyncInfusionToClient;
import com.chamoisest.miningmadness.enums.MachineInfusionEnum;
import com.chamoisest.miningmadness.setup.MessagesSetup;
import com.chamoisest.miningmadness.util.NBTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class BaseInfusionEntity extends BaseEnergyEntity implements IInfusable {

    public static InfusionCapability infusionCapability;
    public static LazyOptional<IInfusionCapability> lazyInfusionHandler = LazyOptional.empty();

    public BaseInfusionEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int invSize) {
        super(pType, pPos, pBlockState, invSize);
        infusionCapability = new InfusionCapability(){
            @Override
            public void onInfusionChanged(MachineInfusionEnum type, int value) {
                setChanged();
                setInfusionChanged(type, value);
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == MiningMadness.INFUSION_CAPABILITY){
            return lazyInfusionHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public InfusionCapability getInfusionCapability(){
        return infusionCapability;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(NBTTags.INFUSION_TAG, infusionCapability.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains(NBTTags.INFUSION_TAG)){
            infusionCapability.deserializeNBT(pTag.getCompound(NBTTags.INFUSION_TAG));
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyInfusionHandler = LazyOptional.of(() -> infusionCapability);
        activateInfusionTypes();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyInfusionHandler.invalidate();
    }

    public void sendUpdateInfusionPacket(MachineInfusionEnum type, int value){
        if(this.level == null) return;
        if(!level.isClientSide()) {
            BlockPos pos = this.getBlockPos();
            LevelChunk chunk = this.level.getChunkAt(pos);
            MessagesSetup.sendToPlayersInChunk(new PacketSyncInfusionToClient(type, value, pos), chunk);
        }
    }

    @Override
    public void setInfusionChanged(MachineInfusionEnum type, int value) {
        sendUpdateInfusionPacket(type, value);
    }

    @Override
    public void activateInfusion(MachineInfusionEnum maxType, int maxValue) {
        if(maxType.isMaxValue()) {
            infusionCapability.setInfusionValue(maxType, maxValue);
            initializeInfusionPacket(maxType, maxValue);
        }
    }
}

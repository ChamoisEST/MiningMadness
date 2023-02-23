package com.chamoisest.miningmadness.common.block.entity.base;

import com.chamoisest.miningmadness.api.MathHelper;
import com.chamoisest.miningmadness.enums.MachineUpgradeEnum;
import com.chamoisest.miningmadness.util.MMEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseMachineBlockEntity extends BlockEntity implements MenuProvider {
    protected final ItemStackHandler itemHandler;

    public final MMEnergyStorage ENERGY_STORAGE;

    protected int energy_req;

    protected int baseEnergyReqForOperation = 1000;
    protected int baseEnergyCapacity = 100000;
    protected int baseRange = 5;

    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected String displayNameTranslatable;

    protected int working = 0;
    protected int statusTooltip = 4;
    protected int redstone = 2;
    protected int redstoneTooltip = 7;

    protected int showRange = 0;
    protected int showRangeTooltip = 11;

    public int drawTopRangeHelperByY = 0;

    protected int speed_infused = 0;
    protected int energy_capacity_infused = 0;
    protected int efficiency_infused = 0;

    protected int fortune_infused = 0;
    protected int silk_touch_infused = 0;
    protected int range_infused = 0;

    protected int progress = 0;
    protected int maxProgress = 60;

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public BaseMachineBlockEntity(BlockPos pos, BlockState state, BlockEntityType beType, int invSize, int baseMaxTransfer, int baseEnergyReq){
        super(beType, pos, state);
        this.energy_req = baseEnergyReq;

        itemHandler = new ItemStackHandler(invSize) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        ENERGY_STORAGE = new MMEnergyStorage(100000, baseMaxTransfer) {
            @Override
            public void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("working", getWorking());
        nbt.putInt("status_tooltip", getStatusTooltip());
        nbt.putInt("redstone", getRedstone());
        nbt.putInt("redstone_tooltip", getRedstoneTooltip());
        nbt.putInt("show_range", getShowRange());
        nbt.putInt("show_range_tooltip", getShowRangeTooltip());
        nbt.putInt("speed_infused", this.speed_infused);
        nbt.putInt("energy_capacity_infused", this.energy_capacity_infused);
        nbt.putInt("efficiency_infused", this.efficiency_infused);
        nbt.putInt("fortune_infused", fortune_infused);
        nbt.putInt("silk_touch_infused", silk_touch_infused);
        nbt.putInt("range_infused", range_infused);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
        this.statusTooltip = nbt.getInt("status_tooltip");
        this.working = nbt.getInt("working");
        this.redstoneTooltip = nbt.getInt("redstone_tooltip");
        this.redstone = nbt.getInt("redstone");
        this.showRange = nbt.getInt("show_range");
        this.showRangeTooltip = nbt.getInt("show_range_tooltip");
        this.speed_infused = nbt.getInt("speed_infused");
        this.energy_capacity_infused = nbt.getInt("energy_capacity_infused");
        this.efficiency_infused = nbt.getInt("efficiency_infused");
        this.fortune_infused = nbt.getInt("fortune_infused");
        this.silk_touch_infused = nbt.getInt("silk_touch_infused");
        this.range_infused = nbt.getInt("range_infused");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(displayNameTranslatable);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    public static <T extends BaseMachineBlockEntity> boolean checkRedstone(Level level, T pEntity){
        int redstone = pEntity.getRedstone();
        boolean response = false;
        return switch (redstone) {
            case 0 -> !level.hasNeighborSignal(pEntity.getBlockPos());
            case 1 -> level.hasNeighborSignal(pEntity.getBlockPos());
            case 2 -> true;
            default ->
                    throw new IllegalStateException("Entity '" + pEntity + "' has an illegal redstone state: '" + redstone + "'");
        };
    }

    public static <T extends BaseMachineBlockEntity> boolean isWorking(T pEntity){
        return pEntity.working == 1;
    }

    public static <T extends BaseMachineBlockEntity> boolean hasError(T pEntity){
        return pEntity.working == 2;
    }

    public void updateWorking(int value){
        this.working = value;
        this.statusTooltip = (value == 0) ? 4 : 3;
    }

    public int getWorking(){
        return this.working;
    }

    public static <T extends BaseMachineBlockEntity> void setStatusTooltip(T pEntity, int tooltipId){
        pEntity.statusTooltip = tooltipId;
    }

    public int getStatusTooltip(){
        return this.statusTooltip;
    }

    public int getRedstone(){
        return this.redstone;
    }

    public void updateRedstone(int value){
        this.redstone = value;
        this.redstoneTooltip = switch(value){
            case 0 -> 5;
            case 1 -> 6;
            case 2 -> 7;
            default -> throw new IllegalStateException("Entity '" + this + "' has an illegal redstone state: '" + this.redstone + "'");
        };
    }

    public int getShowRangeTooltip(){
        return this.showRangeTooltip;
    }

    public int getShowRange(){
        return this.showRange;
    }

    public void updateShowRange(int value){
        this.showRange = value;
        this.showRangeTooltip = switch(value){
            case 0 -> 11;
            case 1 -> 12;
            default -> throw new IllegalStateException("Entity '" + this + "' has an illegal redstone state: '" + this.redstone + "'");
        };
    }

    public int getRedstoneTooltip(){
        return this.redstoneTooltip;
    }

    public static <T extends BaseMachineBlockEntity> void setRedstoneTooltip(T pEntity, int tooltipId){
        pEntity.redstoneTooltip = tooltipId;
    }

    protected static <T extends BaseMachineBlockEntity> void extractEnergy(T pEntity){
        pEntity.ENERGY_STORAGE.extractEnergy(pEntity.energy_req, false);
    }

    protected static <T extends BaseMachineBlockEntity> boolean hasEnoughEnergy(T pEntity){
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= pEntity.energy_req;
    }

    public int getInfused(MachineUpgradeEnum upgrade){
        return switch(upgrade){

            case FORTUNE -> this.fortune_infused;
            case SPEED -> this.speed_infused;
            case EFFICIENCY -> this.efficiency_infused;
            case SILK_TOUCH -> this.silk_touch_infused;
            case RANGE -> this.range_infused;
            case ENERGY_CAPACITY -> this.energy_capacity_infused;
        };
    }

    public void setInfused(MachineUpgradeEnum upgrade, int value){
        switch(upgrade){

            case FORTUNE -> this.fortune_infused = value;
            case SPEED -> this.speed_infused = value;
            case EFFICIENCY -> this.efficiency_infused = value;
            case SILK_TOUCH -> this.silk_touch_infused = value;
            case RANGE -> this.range_infused = value;
            case ENERGY_CAPACITY -> this.energy_capacity_infused = value;
        };
    }

    public int calculateRange(){
        return this.baseRange + this.range_infused;
    }

    public int calculateEnergyCapacity(){
        return this.baseEnergyCapacity + this.energy_capacity_infused * 10000;
    }

    public int calculateEnergyPerTick(){
        int energy_req = this.energy_req;
        float speedMultiplier = 0.3F;
        float efficiencyDecrease = 0.06F;

        energy_req += (int)(energy_req * (Math.pow(speedMultiplier * this.speed_infused, 2) * 0.6) / 100);
        energy_req -= energy_req * Math.min(efficiencyDecrease * this.efficiency_infused, 70) / 100;
        return energy_req;
    }

    public boolean hasSilkTouch(){
        return this.silk_touch_infused == 1;
    }

    public int calculateFortuneLevel(){
        if(this.fortune_infused < 50){
            return 0;
        }else if(MathHelper.between(this.fortune_infused, 50, 160)){
            return 1;
        }else if(MathHelper.between(this.fortune_infused, 160, 350)){
            return 2;
        }else if(MathHelper.between(this.fortune_infused, 350, 600)){
            return 3;
        }else if(MathHelper.between(this.fortune_infused, 600, 950)){
            return 4;
        }else if(MathHelper.between(this.fortune_infused, 950, 1700)){
            return 5;
        }else{
            return 6;
        }
    }

    public int calculateEnergyReqPerOperation(){
        float energy_req = this.baseEnergyReqForOperation;
        float fortuneMultiplier = 0.24F;
        float rangeMultiplier = 1.8F;
        float efficiencyMultiplier = 0.06F;


        energy_req += Math.pow(rangeMultiplier * this.range_infused, 2) * 0.6;
        energy_req += Math.pow(fortuneMultiplier * this.fortune_infused, 2) * 0.6;
        energy_req += 4500 * this.silk_touch_infused;
        energy_req -= energy_req * efficiencyMultiplier * this.efficiency_infused / 100;

        return (int)energy_req;
    }

    public int calculateMaxProgress(){
        return calculateEnergyReqPerOperation() / calculateEnergyPerTick();
    }
}

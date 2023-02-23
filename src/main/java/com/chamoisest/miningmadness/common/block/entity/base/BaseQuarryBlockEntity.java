package com.chamoisest.miningmadness.common.block.entity.base;

import com.chamoisest.miningmadness.util.RangeManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BaseQuarryBlockEntity extends BaseRangeBlockEntity{
    protected BlockPos miningPos = null;
    protected int finished = 0;

    protected int distFront = 0;
    protected int distDown = 0;
    protected int distSide = 0;

    protected int fortune_level = 0;
    public RangeManager rangeManager = null;

    public BaseQuarryBlockEntity(BlockPos pos, BlockState state, BlockEntityType beType, int invSize, int baseMaxTransfer, int baseEnergyReq) {
        super(pos, state, beType, invSize, baseMaxTransfer, baseEnergyReq);
        this.drawTopRangeHelperByY = 3;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putBoolean("has_mining_pos", (miningPos != null));
        if(miningPos != null) {
            nbt.putInt("mining_pos_x", miningPos.getX());
            nbt.putInt("mining_pos_y", miningPos.getY());
            nbt.putInt("mining_pos_z", miningPos.getZ());
        }
        nbt.putInt("dist_front", distFront);
        nbt.putInt("dist_side", distSide);
        nbt.putInt("dist_down", distDown);
        nbt.putInt("finished", finished);
        nbt.putInt("fortune_level", fortune_level);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        boolean hasMiningPos = nbt.getBoolean("has_mining_pos");
        if (hasMiningPos){
            this.miningPos = new BlockPos(
                    nbt.getInt("mining_pos_x"),
                    nbt.getInt("mining_pos_y"),
                    nbt.getInt("basic_quarry.mining_pos_z")
            );
        }
        this.distFront = nbt.getInt("dist_front");
        this.distSide = nbt.getInt("dist_side");
        this.distDown = nbt.getInt("dist_down");
        this.finished = nbt.getInt("finished");
        this.fortune_level = nbt.getInt("fortune_level");
    }

    protected static boolean checkInventoryFull(BaseMachineBlockEntity pEntity) {
        ItemStackHandler handler = pEntity.itemHandler;
        int slots = handler.getSlots();
        for(int slot = 0; slot < slots; slot++){
            if(handler.getStackInSlot(slot) == ItemStack.EMPTY){
                return false;
            }
        }
        return true;
    }

    protected static <T extends BaseQuarryBlockEntity> boolean increaseTick(T pEntity){
        if(pEntity.progress >= pEntity.calculateMaxProgress()){
            pEntity.progress = 0;
            return true;
        }else{
            pEntity.progress += 1;
            return false;
        }
    }

    protected static <T extends BaseQuarryBlockEntity> void mine(T pEntity, Level level){

        BlockPos miningPos = pEntity.miningPos;
        BlockPos startPos = pEntity.rangeManager.getStartPos();

        if (miningPos == null) {
            miningPos = startPos;
        } else {
            miningPos = pEntity.rangeManager.getNextPos(miningPos);
        }

        pEntity.miningPos = miningPos;

        BlockState mineableBlock = level.getBlockState(miningPos);
        if (mineableBlock.is(Blocks.BEDROCK)) {
            pEntity.updateWorking(2);
            setStatusTooltip(pEntity, 8);
            pEntity.finished = 1;
            pEntity.miningPos = null;
        } else if (mineableBlock.is(Blocks.AIR) || mineableBlock.hasBlockEntity()) {
            pEntity.miningPos = miningPos;
            mine(pEntity, level);
        } else {
            pEntity.miningPos = miningPos;
            level.removeBlock(miningPos, false);
            level.playSound(null, miningPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1, 1);

            ItemStack tool = new ItemStack(Items.NETHERITE_PICKAXE);
            int fortuneLevel = pEntity.calculateFortuneLevel();

            if(fortuneLevel > 0) {
                tool.enchant(Enchantments.BLOCK_FORTUNE, fortuneLevel);
            }else if(pEntity.hasSilkTouch()){
                tool.enchant(Enchantments.SILK_TOUCH, 1);
            }

            List<ItemStack> drops = Block.getDrops(mineableBlock, (ServerLevel) level, pEntity.miningPos, null, null, tool);
            putItemIntoInventory(pEntity, drops);
        }
    }

    protected static <T extends BaseQuarryBlockEntity> void putItemIntoInventory(T pEntity, List<ItemStack> drops){
        ItemStackHandler handler = pEntity.itemHandler;
        int slots = handler.getSlots();
        for (ItemStack drop : drops) {
            for(int slot = 0; slot < slots; slot++){
                drop = handler.insertItem(slot, drop, false);
                if(drop != ItemStack.EMPTY){
                    if(slot >= slots - 1){
                        setInventoryFull(pEntity);
                        return;
                    }
                }
            }
        }
    }

    protected static <T extends BaseQuarryBlockEntity> void setInventoryFull(T pEntity){
        pEntity.updateWorking(2);
        setStatusTooltip(pEntity, 2);
    }
}

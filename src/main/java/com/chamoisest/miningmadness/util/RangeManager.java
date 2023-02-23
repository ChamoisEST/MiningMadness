package com.chamoisest.miningmadness.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RangeManager {
    private final BlockPos startPos;
    private final BlockPos endPos;
    private AABB rangeBox;
    private final int range;
    private final Direction direction;

    public RangeManager(BlockEntity entity, int range, Direction direction, int dirOffset, int yOffset, int height){
        this.startPos = entity.getBlockPos().above(yOffset).relative(direction, dirOffset);

        this.endPos = this.startPos.relative(direction, range).relative(direction.getClockWise(), range).below(height);
        this.range = range;
        this.direction = direction;
        this.rangeBox = new AABB(0,0,0,1,1,1).move(startPos);
        inflate();
    }

    public VoxelShape getShape(){
        return Shapes.create(this.rangeBox);
    }

    public Direction getDirection(){
        return this.direction;
    }

    public AABB getRangeBox(){
        return this.rangeBox;
    }

    public BlockPos getStartPos(){
        return this.startPos;
    }

    public int getRange(){
        return this.range;
    }

    public void inflate(){
        AABB rangeBox = this.rangeBox;
        int x = this.endPos.getX() - this.startPos.getX();
        int y = this.endPos.getY() - this.startPos.getY();
        int z = this.endPos.getZ() - this.startPos.getZ();
        this.rangeBox = rangeBox.expandTowards(x, y, z);
    }

    public boolean isInRangeBox(BlockPos position){
        return this.rangeBox.contains(position.getX(), position.getY(), position.getZ());
    }

    public BlockPos getNextPos(BlockPos currentPos){
        BlockPos frontPos = currentPos.relative(this.getDirection(), 1);
        BlockPos rightPos = currentPos.relative(this.getDirection().getClockWise(), 1).relative(this.getDirection(), -this.getRange());
        BlockPos bottomPos = new BlockPos(getStartPos().getX(), currentPos.getY()-1, getStartPos().getZ());
        if(isInRangeBox(frontPos)){
            return frontPos;
        }else if(isInRangeBox(rightPos)){
            return rightPos;
        }else if(isInRangeBox(bottomPos)){
            return bottomPos;
        }
        return null;
    }

}

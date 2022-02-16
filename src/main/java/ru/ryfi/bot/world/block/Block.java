/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.world.block;

import ru.ryfi.bot.world.position.BlockLocation;

public class Block {
    private BlockLocation position;
    private BlockType blockType;
    public Block(BlockLocation position, BlockType blockType){
        this.position = position;

        this.blockType = blockType;


    }

    public BlockType getMaterial() {
        return blockType;
    }

    public void setMaterial(BlockType blockType) {

            this.blockType = blockType;
    }

    public BlockLocation getPosition() {
        return position;
    }


}

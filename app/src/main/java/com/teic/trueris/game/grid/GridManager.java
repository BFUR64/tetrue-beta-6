package com.teic.trueris.game.grid;

import com.teic.trueris.Config;

public class GridManager {
    // New Role Change: GridManger is a high level 
    // interaction layer between the GameLoop and 
    // the moving of blocks 
    // BlockData is now a dumb class 
    
    private final GridData gridData;
    private BlockData block;
    private BlockData blockHighlight;
    private Collision collision;

    public GridManager(
        Config config, 
        GridData gridData, 
        BlockData blockData
    ) {
        this.gridData = gridData;
        this.block = blockData;
        this.blockHighlight = block.copy();

        collision = new Collision(config, gridData);
    }

    /*
     * Idk if this is the right approach 
     */

    private void writeBlockToGrid() {

    }

    public void dropBlock() {
        while (collision.isPositionValid(block)) {
            block.moveDown();
        }

        block.revertPosition();
        // Place block down
    }

    public boolean moveBlockDown() {
        block.moveDown();

        if (!collision.isPositionValid(block)) {
            block.revertPosition();
            
            // Place block down 
            // return bool to imply it locked
            return false;
        }

        // return bool to imply it moved
        return true;
    }
}


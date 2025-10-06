package com.teic.trueris.game.grid;

import com.teic.trueris.Config;

public class GridManager {
    // New Role Change: GridManager should manage setting 
    // and getting cells in GridData. Change BlockManager to 
    // BlockData instead? Smart enough to validate its pos, 
    // but not too smart that it does Grid-level ops 
    //
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


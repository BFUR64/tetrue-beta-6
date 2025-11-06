package com.teic.trueris.game.grid;

import com.teic.trueris.Config;
import com.teic.trueris.game.templates.BlockTemplate;
import com.teic.trueris.game.templates.CellTemplate;

public class GridManager {
    // New Role Change: GridManger is a high level 
    // interaction layer between the GameLoop and 
    // the moving of blocks 
    // BlockData is now a dumb class 

    // Shouldn't BlockData be initialized by GridManager? Only Config GridData should be passed on?
    
    private final GridData gridData;
    private final Collision collision;

    private BlockData block;
    private BlockData ghostBlock;

    public GridManager(
        Config config, 
        GridData gridData
    ) {
        this.gridData = gridData;
        collision = new Collision(config, gridData);

        block = new BlockData(BlockTemplate.copyBlock(
            (int) Math.round(Math.random() * BlockTemplate.BLOCK_SET_CAPACITY)
        ));

        ghostBlock = block.copy();

    }

    // private void writeBlockToGrid() {
        // CellTemplate[][] copy = block.getRotatedBlockCopy();

        // for (int row = 0; row < copy.length; row++) {
        //     for (int col = 0; col < copy[0].length; col++) {
        //         gridData.setCell(copy[row + block.blockRow()][col + block.blockCol()], row, col);
        //     }
        // }
    // }

    // private void writeHighlighToPreviewGrid() {

    // }

    private void writeToGrid(BlockData blockData) {

    }

    private void writeToBlockGrid() {
        
    }
    
    private void writeToPreviewGrid(BlockData blockData) {

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
            // Erase blockGrid? 
            // Erase previewGrid?
            
            // return bool to imply it locked
            return false;
        }

        // return bool to imply it moved
        return true;
    }
}


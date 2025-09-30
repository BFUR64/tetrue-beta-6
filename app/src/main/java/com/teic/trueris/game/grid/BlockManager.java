package com.teic.trueris.game.grid;

import com.teic.trueris.game.templates.CellTemplate;

public class BlockManager {
    // Make helper methods
    // e.g. spawnBlock() -- ACTUALLY SPAWNS IT, NOT INITIALIZES THE CLASS!!!!!
    // moveBlock() -- ACTUALLY MOVES IT 
    // rotateBlock() -- ACTUALLY ROTATES AND UPDATES IN THE GRID 
    // // Hmm... Maybe make it return a bool for each to indicate if it's successful or not? 

    private CellTemplate[][] block;
    private int blockRowPos;
    private int blockColPos;

    // Hmm... This does not seem like a BlockManager job?
    /*public boolean spawnBlock(CellTemplate[][] block) {
        blockRowPos = 0;
        blockColPos = 3;

        this.block = block;
    }*/

    public boolean moveBlock() {

    }

    public boolean rotateBlock() {

    }

    // Helper methods?
    public int blockRowPos() {
        return blockRowPos;
    }

    public int blockColPos() {
        return blockColPos;
    }

    public int getBlockSize() {
        return block.length;
    }

    public CellTemplate getCell(int row, int col) {
        return block[row][col];
    }
}


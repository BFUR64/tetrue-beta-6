package com.teic.trueris.game.grid;

import com.teic.trueris.game.templates.CellTemplate;

public class BlockManager {
    // Make helper methods
    // e.g. spawnBlock() -- ACTUALLY SPAWNS IT, NOT INITIALIZES THE CLASS!!!!!
    // moveBlock() -- ACTUALLY MOVES IT 
    // rotateBlock() -- ACTUALLY ROTATES AND UPDATES IN THE GRID 
    // // Hmm... Maybe make it return a bool for each to indicate if it's successful or not? 

    private final BlockCollision blockCollision;

    private CellTemplate[][] block;
    private int blockRowPos;
    private int blockColPos;

    public BlockManager(BlockCollision blockCollision) {
        this.blockCollision = blockCollision;
    }

    // Hmm... This does not seem like a BlockManager job?
    /*public boolean spawnBlock(CellTemplate[][] block) {
        blockRowPos = 0;
        blockColPos = 3;

        this.block = block;
    }*/

    // moveDown returns true if it moved, or else false?
    // Same goes for the other methods, I guess, including 
    // rotation?
    //
    // Do I auto place??? Maybe...
    // It makes sense if it autoPlaces though, no? 
    // But the name does not match behavior 
    // But it is tetris, not like its gonna change the 
    // behavioe whether or not it auto places no? cuz 
    // in tetris theres onp6 one thing that happens if it 
    // collides, it reverts to the og rowPos theb places 
    //

    public boolean moveDown() {
        blockRowPos++;

        if (blockCollision.isValid(this)) {
            return true;
        } else {
            blockRowPos--;
            return false;
        }
        
    }

    public boolean moveLeft() {

    }

    public boolean moveRight() {

    }

    // Since placeDown guarantees it'll be always placed 
    // unlike the other methods, it should not return a 
    // boolean
    // ... Maybe a better name though 
    public void placeDown() {

    }

    public boolean rotateLeft() {

    }

    public boolean rotateRight() {

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


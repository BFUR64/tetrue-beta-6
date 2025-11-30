package com.teic.trueris.game.block;

import com.teic.trueris.Config;
import com.teic.trueris.game.templates.CellTemplate;

public class BlockData {
    private final CellTemplate[][] block;
    private Direction blockRotation = Direction.UP;
    private int blockRow;
    private int blockCol;

    private Direction prevBlockRotation = blockRotation;
    private int prevBlockRow;
    private int prevBlockCol;
    
    public BlockData(CellTemplate[][] block) {
        this.block = block;
        this.blockCol = (int) Config.getBlockOffset();
    }

    private BlockData(CellTemplate[][] block, int blockRow, int blockCol, Direction blockRotation) {
        this.block = block;
        this.blockRow = blockRow;
        this.blockCol = blockCol;
        this.blockRotation = blockRotation;
    }

    // =====================
    // Movement
    // =====================
    public void moveDown() {
        prevBlockRow = blockRow;
        blockRow++;
    }

    public void moveLeft() {
        prevBlockCol = blockCol;
        blockCol--;
    }

    public void moveRight() {
        prevBlockCol = blockCol;
        blockCol++;
    }

    // public void revertPosition() {
    //     blockRow = prevBlockRow;
    //     prevBlockRow = 0;

    //     blockCol = prevBlockCol;
    //     prevBlockCol = 0;
    // }

    public void revertRowPosition() {
        blockRow = prevBlockRow;
        prevBlockRow = 0;
    }

    public void revertColPosition() {
        blockCol = prevBlockCol;
        prevBlockCol = 0;
    }

    // =====================
    // Rotation
    // =====================
    public void rotateLeft() {
        Direction[] directions = Direction.values();

        int rotationIndex = (
            ((blockRotation.ordinal() - 1) % 4 + 4) % 4
        );

        prevBlockRotation = blockRotation;
        
        blockRotation = directions[rotationIndex];
    }

    public void rotateRight() {
        Direction[] directions = Direction.values();

        int rotationIndex = (
            ((blockRotation.ordinal() + 1) % 4 + 4) % 4
        );

        prevBlockRotation = blockRotation;
        
        blockRotation = directions[rotationIndex];
    }

    public void revertBlockRotation() {
        blockRotation = prevBlockRotation;
        prevBlockRotation = Direction.UP;
    }

    // =====================
    // Block Array Copying
    // =====================

    // rotateArrayRight → used by rotateBlockNTimes → used by getRotatedBlockCopy

    public CellTemplate[][] getRotatedBlockCopy() {
        return rotateBlockNTimes(blockRotation.ordinal());
    }

    private CellTemplate[][] rotateBlockNTimes(int amount) {
        CellTemplate[][] newBlock = copy(block);

        for (int i = 0; i < amount; i++) {
            newBlock = rotateArrayRight(newBlock);
        }

        return newBlock;
    }

    private CellTemplate[][] rotateArrayRight(
        CellTemplate[][] block
    ) {
        int blockSize = block.length;
        CellTemplate[][] newBlock = 
            new CellTemplate[blockSize][blockSize];

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                newBlock[row][blockSize - 1 - col] = 
                    block[col][row];
            }
        }

        return newBlock;
    }

    // =====================
    // Block Object Copying
    // =====================
    public BlockData copyBlockData() {
        return new BlockData(copy(this.block), this.blockRow, this.blockCol, this.blockRotation);
    }

    private CellTemplate[][] copy(
        CellTemplate[][] original
    ) {
        int blockSize = original.length;
        CellTemplate[][] copy = 
            new CellTemplate[blockSize][blockSize];

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                copy[row][col] = original[row][col];
            }
        }

        return copy;
    }

    // =====================
    // Size / Position Info
    // =====================
    public int blockRow() {
        return blockRow;
    }

    public int blockCol() {
        return blockCol;
    }

    public int blockSize() {
        return block.length;
    }
}

enum Direction {
    UP, RIGHT, DOWN, LEFT;
}


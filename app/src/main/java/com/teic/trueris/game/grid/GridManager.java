package com.teic.trueris.game.grid;

import java.util.Scanner;

import com.teic.trueris.Config;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;
import com.teic.trueris.game.templates.CellTemplate;

public class GridManager {
    private final Collision collision;

    private final Config config;

    private final GridData gridData;

    private final BlockQueue blockQueue;

    private BlockData activeBlock;
    private BlockData ghostBlock;

    public GridManager(Config config, GridData gridData) {
        this.gridData = gridData;
        this.config = config;

        collision = new Collision(config, gridData);

        blockQueue = new BlockQueue();

        activeBlock = new BlockData(blockQueue.getRandomBlock());

        ghostBlock = activeBlock.copyBlockData();
    }

    // =====================
    // Movement
    // =====================
    public boolean moveBlockDown() {
        activeBlock.moveDown();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertRowPosition();

            writeToSolidGrid(activeBlock);
            gridData.eraseActiveGrid();
            gridData.eraseGhostGrid();

            activeBlock = new BlockData(blockQueue.getRandomBlock());
            // ghostBlock = activeBlock.copyBlockData();

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }

    public void dropBlock() {
        while (moveBlockDown()) {}
    }

    public boolean moveBlockLeft() {
        activeBlock.moveLeft();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertColPosition();

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }

    public boolean moveBlockRight() {
        activeBlock.moveRight();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertColPosition();

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }
    
    // =====================
    // Rotation
    // =====================
    public boolean rotateBlockRight() {
        activeBlock.rotateRight();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertBlockRotation();

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }

    public boolean rotateBlockLeft() {
        activeBlock.rotateLeft();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertBlockRotation();

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }

    // =====================
    // Grid Manipulation
    // =====================
    private void writeToSolidGrid(BlockData blockData) {
        CellTemplate[][] block = blockData.getRotatedBlockCopy();

        int blockRow = blockData.blockRow();
        int blockCol = blockData.blockCol();

        int blockSize = blockData.blockSize();

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                CellTemplate cell = block[row][col];

                if (cell.isEmpty()) {
                    continue;
                }

                gridData.setSolidCell(cell, row + blockRow, col + blockCol);
            }
        }
    }

    private void writeToActiveGrid(BlockData blockData) {
        CellTemplate[][] block = blockData.getRotatedBlockCopy();

        int blockRow = blockData.blockRow();
        int blockCol = blockData.blockCol();

        int blockSize = blockData.blockSize();

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                CellTemplate cell = block[row][col];

                if (cell.isEmpty()) {
                    continue;
                }

                gridData.setActiveCell(cell, row + blockRow, col + blockCol);
            }
        }
    }
}


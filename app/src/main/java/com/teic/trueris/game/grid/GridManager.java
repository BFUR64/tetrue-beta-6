package com.teic.trueris.game.grid;

import com.teic.trueris.Config;
import com.teic.trueris.game.templates.CellTemplate;

public class GridManager {
    // Private methods to write to Grid

    // Public methods to move block

    // GameLoop shouldn't manage spawning of new Blocks, placing of blocks, etc., just returns bools if it gets locked to grid
    // GridManager should manage the above, but not be integrated, so another class for giving new blocks, spawning a seven-bag, etc.
    private final Collision collision;

    private final GridData gridData;

    private final BlockQueue blockQueue;

    private BlockData activeBlock;
    private BlockData ghostBlock;

    public GridManager(Config config, GridData gridData) {
        this.gridData = gridData;

        collision = new Collision(config, gridData);

        blockQueue = new BlockQueue();

        activeBlock = new BlockData(blockQueue.getRandomBlock());

        ghostBlock = activeBlock.copyBlockData();
    }

    public boolean moveBlockDown() {
        activeBlock.moveDown();

        boolean isPositionValid = collision.isPositionValid(activeBlock);

        if (!isPositionValid) {
            activeBlock.revertPosition();

            writeToSolidGrid(activeBlock);
            gridData.eraseActiveGrid();
            gridData.eraseGhostGrid();

            // Spawn a new block?
            // Spawn a new ghostBlock?

            return false;
        }

        gridData.eraseActiveGrid();
        writeToActiveGrid(activeBlock);

        return true;
    }

    public void dropBlock() {

    }

    public boolean moveBlockLeft() {
        return false;
    }

    public boolean moveBlockRight() {
        return false;
    }

    private void writeToSolidGrid(BlockData blockData) {
        CellTemplate[][] block = blockData.getRotatedBlockCopy();

        int blockRow = blockData.blockRow();
        int blockCol = blockData.blockCol();

        int blockSize = blockData.blockSize();

        for (int row = blockRow; row < blockSize; row++) {
            for (int col = blockCol; col < blockSize; col++) {
                CellTemplate cell = block[row][col];

                if (cell.isEmpty()) {
                    continue;
                }

                gridData.setSolidCell(cell, row, col);
            }
        }
    }

    private void writeToActiveGrid(BlockData blockData) {
        CellTemplate[][] block = blockData.getRotatedBlockCopy();

        int blockRow = blockData.blockRow();
        int blockCol = blockData.blockCol();

        int blockSize = blockData.blockSize();

        for (int row = blockRow; row < blockSize; row++) {
            for (int col = blockCol; col < blockSize; col++) {
                CellTemplate cell = block[row][col];

                if (cell.isEmpty()) {
                    continue;
                }

                gridData.setActiveCell(cell, row, col);
            }
        }
    }
}


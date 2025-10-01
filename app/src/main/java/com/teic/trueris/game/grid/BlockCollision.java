package com.teic.trueris.game.grid;

import com.teic.trueris.Config;

public class BlockCollision {
    private Config config;
    private GridData gridData;

    public BlockCollision(Config config, GridData gridData) {
        this.config = config;
        this.gridData = gridData;
    }

    public boolean isValid(BlockManager blockManager) {
        int blockSize = blockManager.getBlockSize();
        int blockRowPos = blockManager.blockRowPos();
        int blockColPos = blockManager.blockColPos();

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                if (blockManager.getCell(row, col).isEmpty()) {
                    continue;
                }

                int gridRow = blockRowPos + row;
                int gridCol = blockColPos + col;

                if (
                    isOutOfBounds(gridRow, gridCol)
                    || isColliding(gridRow, gridCol)
                ) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isOutOfBounds(int gridRow, int gridCol) {
        return (
            gridRow < 0 || gridRow >= config.getHeight()
            || gridCol < 0 || gridCol >= config.getWidth()
        );
    }

    private boolean isColliding(int gridRow, int gridCol) {
        return gridData.getCell(gridRow, gridCol).isPlaced();
    }
}


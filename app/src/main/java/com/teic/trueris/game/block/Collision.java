package com.teic.trueris.game.block;

import com.teic.trueris.Config;
import com.teic.trueris.game.grid.GridData;
import com.teic.trueris.game.templates.CellTemplate;

public class Collision {
    private final Config config;
    private final GridData gridData;

    public Collision(Config config, GridData gridData) {
        this.config = config;
        this.gridData = gridData;
    }

    public boolean isPositionValid(BlockData blockData) {
        CellTemplate[][] block = blockData.getRotatedBlockCopy();

        int blockSize = block.length;
        int blockRow = blockData.blockRow();
        int blockCol = blockData.blockCol();

        for (int row = 0; row < blockSize; row++) {
            for (int col = 0; col < blockSize; col++) {
                if (block[row][col].isEmpty()) {
                    continue;
                }

                int gridRow = blockRow + row;
                int gridCol = blockCol + col;

                if (
                    isOutOfBounds(gridRow, gridCol)
                    || isColliding(gridRow, gridCol)
                ) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isOutOfBounds(int gridRow, int gridCol) {
        return (
            gridRow < 0 || gridRow >= config.getHeight() + config.getBuffer()
            || gridCol < 0 || gridCol >= config.getWidth()
        );
    }

    private boolean isColliding(int gridRow, int gridCol) {
        return !gridData.getSolidCell(gridRow, gridCol).isEmpty();
    }
}


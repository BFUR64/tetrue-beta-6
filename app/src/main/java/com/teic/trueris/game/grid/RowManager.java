package com.teic.trueris.game.grid;

import com.teic.trueris.Config;
import com.teic.trueris.game.block.BlockData;
import com.teic.trueris.game.templates.CellTemplate;

public class RowManager {
    private final Config config;
    private final GridData gridData;

    public RowManager(Config config, GridData gridData) {
        this.config = config;
        this.gridData = gridData;
    }

    public void writeGrid(GridType gridType, BlockData blockData) {
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

                switch (gridType) {
                    case SOLID -> { gridData.setSolidCell(cell, row + blockRow, col + blockCol); }
                    case ACTIVE -> { gridData.setActiveCell(cell, row + blockRow, col + blockCol); }
                    case GHOST -> { gridData.setGhostCell(cell, row + blockRow, col + blockCol); }
                }
            }
        }
    }

    public void eraseGrid(GridType gridType) {
        int gridRow = (int) (config.getHeight() + config.getBuffer());
        int gridCol = (int) config.getWidth();

        for (int row = 0; row < gridRow; row++) {
            for (int col = 0; col < gridCol; col++) {
                switch (gridType) {
                    case SOLID -> { gridData.setSolidCell(CellTemplate.EMPTY, row, col); }
                    case ACTIVE -> { gridData.setActiveCell(CellTemplate.EMPTY, row, col); }
                    case GHOST -> { gridData.setGhostCell(CellTemplate.EMPTY, row, col); }
                }
            }
        }
    }

    public void clearFilledRows() {
        clearFilledRows(returnFilledRows());
    }

    private void clearFilledRows(boolean[] filledRows) {
        boolean hasFilled = false;
        for (int row = 0; row < filledRows.length; row++) {
            if (!filledRows[row]) continue;

            hasFilled = true;
            shiftSolidGridRowFrom(row);
        }

        if (hasFilled) clearFirstRow();
    }

    private boolean[] returnFilledRows() {
        int totalGridRow = (int) (config.getHeight() + config.getBuffer());
        boolean[] filledRows = new boolean[totalGridRow];

        for (int row = 0; row < totalGridRow; row++) {
            boolean isEmpty = false;
            for (int col = 0; col < config.getWidth(); col++) {
                CellTemplate cell = gridData.getSolidCell(row, col);

                if (!cell.isEmpty()) continue;

                isEmpty = true;
                break;
            }

            if (isEmpty) continue;

            filledRows[row] = true;
        }

        return filledRows;
    }

    private void shiftSolidGridRowFrom(int rowStart) {
        for (int row = rowStart; row > 0; row--) {
            for (int col = 0; col < config.getWidth(); col++) {
                CellTemplate cell = gridData.getSolidCell(row - 1, col);
                gridData.setSolidCell(cell, row, col);
            }
        }
    }

    private void clearFirstRow() {
        for (int col = 0; col < config.getWidth(); col++) {
            gridData.setSolidCell(CellTemplate.EMPTY, 0, col);
        }
    }
}


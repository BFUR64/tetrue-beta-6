package com.teic.trueris.game.grid;

import com.teic.trueris.Config;
import com.teic.trueris.game.templates.CellTemplate;

public class GridData {
    private final CellTemplate[][] cellGrid;
    private final CellTemplate[][] blockGrid;
    private final CellTemplate[][] previewGrid;

    public GridData(Config config) {
        int rows = (int) (
            config.getHeight() + config.getBuffer()
        );
        int cols = (int) config.getWidth();

        cellGrid = new CellTemplate[rows][cols];
        blockGrid = new CellTemplate[rows][cols];
        previewGrid = new CellTemplate[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellGrid[row][col] = CellTemplate.EMPTY;
                blockGrid[row][col] = CellTemplate.EMPTY;
                previewGrid[row][col] = CellTemplate.EMPTY;
            }
        }
    }

    public CellTemplate getCell(int row, int col) {
        return cellGrid[row][col];
    }

    public CellTemplate getBlockCell(int row, int col) {
        return blockGrid[row][col];
    }

    public CellTemplate getPreviewCell(int row, int col) {
        return previewGrid[row][col];
    }

    void setCell(CellTemplate cell, int row, int col) {
        cellGrid[row][col] = cell;
    }

    void setBlock(CellTemplate cell, int row, int col) {
        blockGrid[row][col] = cell;
    }

    void setPreviewCell(CellTemplate cell, int row, int col) {
        previewGrid[row][col] = cell;
    }
}


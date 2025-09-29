package com.teic.trueris.game.grid;


import com.teic.trueris.Config;
import com.teic.trueris.game.templates.CellTemplate;

public class GridData {
    private final CellTemplate[][] cellGrid;

    private final Config config;

    public GridData(Config config) {
        this.config = config;

        int rows = (int) (
            config.getHeight() + config.getBuffer()
        );
        int cols = (int) config.getWidth();
        cellGrid = new CellTemplate[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellGrid[row][col] = CellTemplate.EMPTY;
            }
        }
    }

    public CellTemplate getCell(int row, int col) {
        return cellGrid[row][col];
    }
}


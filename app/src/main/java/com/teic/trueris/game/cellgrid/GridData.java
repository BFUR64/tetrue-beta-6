package com.teic.trueris.game.cellgrid;


import com.teic.trueris.Config;
import com.teic.trueris.game.cellmap.Cell;

public class GridData {
    private final Cell[][] cellGrid;

    private final Config config;

    public GridData(Config config) {
        this.config = config;

        cellGrid = new Cell[
            (int) (config.getHeight() + config.getBuffer())
        ][
            (int) config.getWidth()
        ];

        for (int row = 0; row < cellGrid.length; row++) {
            for (int col = 0; col < cellGrid[0].length; col++) {
                cellGrid[row][col] = Cell.EMPTY;
            }
        }
    }
}


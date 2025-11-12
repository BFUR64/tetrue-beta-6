package com.teic.trueris.game.grid;

import com.teic.trueris.Config;
import com.teic.trueris.game.templates.CellTemplate;

public class GridData {
    private final CellTemplate[][] solidGrid;
    private final CellTemplate[][] activeGrid;
    private final CellTemplate[][] ghostGrid;

    private final int gridRow;
    private final int gridCol;

    public GridData(Config config) {
        gridRow = (int) (
            config.getHeight() + config.getBuffer()
        );
        gridCol = (int) config.getWidth();

        solidGrid = new CellTemplate[gridRow][gridCol];
        activeGrid = new CellTemplate[gridRow][gridCol];
        ghostGrid = new CellTemplate[gridRow][gridCol];

        for (int row = 0; row < gridRow; row++) {
            for (int col = 0; col < gridCol; col++) {
                solidGrid[row][col] = CellTemplate.EMPTY;
                activeGrid[row][col] = CellTemplate.EMPTY;
                ghostGrid[row][col] = CellTemplate.EMPTY;
            }
        }
    }

    public CellTemplate getSolidCell(int row, int col) {
        return solidGrid[row][col];
    }

    public CellTemplate getActiveCell(int row, int col) {
        return activeGrid[row][col];
    }

    public CellTemplate getGhostCell(int row, int col) {
        return ghostGrid[row][col];
    }

    void setSolidCell(CellTemplate cell, int row, int col) {
        solidGrid[row][col] = cell;
    }

    void setActiveCell(CellTemplate cell, int row, int col) {
        activeGrid[row][col] = cell;
    }

    void setGhostCell(CellTemplate cell, int row, int col) {
        ghostGrid[row][col] = cell;
    }

    public void eraseActiveGrid() {
        for (int row = 0; row < gridRow; row++) {
            for (int col = 0; col < gridCol; col++) {
                activeGrid[row][col] = CellTemplate.EMPTY;
            }
        }
    }

    public void eraseGhostGrid() {
        for (int row = 0; row < gridRow; row++) {
            for (int col = 0; col < gridCol; col++) {
                ghostGrid[row][col] = CellTemplate.EMPTY;
            }
        }
    }
}


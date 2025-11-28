package com.teic.trueris.game;


import java.io.IOException;
import java.util.InputMismatchException;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.TextColor.Indexed;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.Config;
import com.teic.trueris.game.grid.GridData;
import com.teic.trueris.game.templates.CellTemplate;
import com.teic.trueris.game.templates.Color;

public class Renderer {
    private final Terminal terminal;
    private final TextGraphics textGraphics;
    private final GridData gridData;
    private final Config config;

    private final int borderHeight;
    private final int borderWidth;

    public Renderer(
        Terminal terminal, 
        TextGraphics textGraphics, 
        GridData gridData, 
        Config config
    ) {
        this.terminal = terminal;
        this.textGraphics = textGraphics;
        this.config = config;
        this.gridData = gridData;

        borderHeight = (int) config.getHeight() + 2;
        borderWidth = (int) config.getWidth() + 2;
    }

    public void renderBorder() throws IOException {
        for (int row = 0; row < borderHeight; row++) {
            for (int col = 0; col < borderWidth; col++) {
                if (
                    row == 0 || row == borderHeight -1 
                    || col == 0 || col == borderWidth - 1 
                    ) {
                    drawTile(
                        col, 
                        row, 
                        "" + Symbols.BLOCK_SPARSE, 
                        Color.GREY
                    );
                    continue;
                }
            }
        }
    }

    public void updateScreen() {
        clearBorderContents();

        for (int row = 0; row < (int) config.getHeight(); row++) {
            for (int col = 0; col < (int) config.getWidth(); col++) {
                CellTemplate cell = gridData.getSolidCell(row, col);
                if (!cell.isEmpty()) {
                    drawTile(col + 1, row + 1, "" + Symbols.BLOCK_SOLID, cell.color);
                }

                cell = gridData.getGhostCell(row, col);
                if (!cell.isEmpty()) {
                    drawTile(col + 1, row + 1, "" + Symbols.BLOCK_SPARSE, cell.color);
                }

                cell = gridData.getActiveCell(row, col);
                if (!cell.isEmpty()) {
                    drawTile(col + 1, row + 1, "" + Symbols.BLOCK_SOLID, cell.color);
                }
            }
        }
    }

    private void clearBorderContents() {
        for (int row = 0; row < (int) config.getHeight(); row++) {
            for (int col = 0; col < (int) config.getWidth(); col++) {
                drawTile(col + 1, row + 1, " ", Color.DEFAULT);
            }
        }
    }
    
    private TextColor getTextColor(Color color) {
        return switch (color) {
            case DEFAULT -> ANSI.DEFAULT;
            case GREY -> Indexed.fromRGB(96, 96, 96);
            case YELLOW -> Indexed.fromRGB(205, 205, 0);
            case BLUE -> Indexed.fromRGB(0, 0, 205);
            case ORANGE -> Indexed.fromRGB(205, 102, 0);
            case GREEN -> Indexed.fromRGB(0, 205, 0);
            case RED -> Indexed.fromRGB(205, 0, 0);
            case PURPLE -> Indexed.fromRGB(154, 0, 205);
            case CYAN -> Indexed.fromRGB(0, 205, 205);
            case WHITE -> ANSI.WHITE;
            default -> throw new InputMismatchException("Undefined color");
        };
    }
    
    private void drawTile(
        int col, 
        int row, 
        String out, 
        Color color
    ) {
        textGraphics.setBackgroundColor(getTextColor(color));
        textGraphics.setForegroundColor(getTextColor(color));
        
        textGraphics.putString(col * 2, row, out);
        textGraphics.putString(col * 2 + 1, row, out);
        
        textGraphics.setBackgroundColor(getTextColor(Color.DEFAULT));
        textGraphics.setForegroundColor(getTextColor(Color.DEFAULT));
    }
}

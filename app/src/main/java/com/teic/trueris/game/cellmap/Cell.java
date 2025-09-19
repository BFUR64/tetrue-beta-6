package com.teic.trueris.game.cellmap;

public class Cell {
    public static final Cell EMPTY = new Cell();
    public static final Cell OCELL = new Cell(Color.YELLOW);
    public static final Cell JCELL = new Cell(Color.BLUE);
    public static final Cell LCELL = new Cell(Color.ORANGE);
    public static final Cell SCELL = new Cell(Color.GREEN);
    public static final Cell ZCELL = new Cell(Color.RED);
    public static final Cell TCELL = new Cell(Color.PURPLE);
    public static final Cell ICELL = new Cell(Color.CYAN);

    public final Color COLOR;
    private boolean isPlaced;
    private boolean isHighlight;
    private boolean isCopy;

    private Cell() {
        this.COLOR = Color.DEFAULT;
    }

    private Cell(Color color) {
        this.COLOR = color;
    }

    private Cell(Cell og) {
        this.COLOR = og.COLOR;
        this.isPlaced = og.isPlaced;
        this.isHighlight = og.isHighlight;
        this.isCopy = true;
    }

    public Cell copy() {
        if (isEmpty())
            throw new IllegalStateException("Cannot copy a singleton empty cell");

        return new Cell(this);
    }

    public boolean isEmpty() {
        return this == Cell.EMPTY;
    }

    public boolean isPlaced() { return isPlaced; }
    public boolean isHighlight() { return isHighlight; }
    
    public void setPlaced(boolean value) {
        if (!isCopy)
            throw new IllegalStateException("Cannot modify a cell template");

        if (value && isHighlight)
            throw new IllegalStateException("Placed and Highlight cannot both be true");

        this.isPlaced = value;
    }

    public void setHighlight(boolean value) throws IllegalStateException {
        if (!isCopy) 
            throw new IllegalStateException("Cannot modify a cell template");

        if (value && isPlaced) 
            throw new IllegalStateException("Highlight and Placed cannot both be true");

        this.isHighlight = value;
    }
}


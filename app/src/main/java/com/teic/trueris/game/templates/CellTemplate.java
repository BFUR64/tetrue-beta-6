package com.teic.trueris.game.templates;

public class CellTemplate {
    public static final CellTemplate EMPTY = new CellTemplate();
    public static final CellTemplate OCELL = new CellTemplate(Color.YELLOW);
    public static final CellTemplate JCELL = new CellTemplate(Color.BLUE);
    public static final CellTemplate LCELL = new CellTemplate(Color.ORANGE);
    public static final CellTemplate SCELL = new CellTemplate(Color.GREEN);
    public static final CellTemplate ZCELL = new CellTemplate(Color.RED);
    public static final CellTemplate TCELL = new CellTemplate(Color.PURPLE);
    public static final CellTemplate ICELL = new CellTemplate(Color.CYAN);

    public final Color color;
    private boolean isPlaced;
    // private boolean isHighlight;
    private boolean isCopy;

    private CellTemplate() {
        this.color = Color.DEFAULT;
    }

    private CellTemplate(Color color) {
        this.color = color;
    }

    private CellTemplate(CellTemplate original) {
        this.color = original.color;
        this.isPlaced = original.isPlaced;
        // this.isHighlight = og.isHighlight;
        this.isCopy = true;
    }

    public CellTemplate copy() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot copy a singleton empty cell");
        }

        return new CellTemplate(this);
    }

    public boolean isEmpty() {
        return this == CellTemplate.EMPTY;
    }

    public boolean isPlaced() { return isPlaced; }
    // public boolean isHighlight() { return isHighlight; }
    
    public void setPlaced(boolean value) {
        if (!isCopy) {
            throw new IllegalStateException("Cannot modify a cell template");
        }
        /*
        if (value && isHighlight) {
            throw new IllegalStateException("Placed and Highlight cannot both be true");
        }
        */

        this.isPlaced = value;
    }

    public void setHighlight(boolean value) {
        if (!isCopy) {
            throw new IllegalStateException("Cannot modify a cell template");
        }
        /*
        if (value && isPlaced) {
            throw new IllegalStateException("Highlight and Placed cannot both be true");
        }
        */

        this.isHighlight = value;
    }
}


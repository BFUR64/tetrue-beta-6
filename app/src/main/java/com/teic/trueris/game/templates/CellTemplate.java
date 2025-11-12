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
    private boolean isCopy;

    private CellTemplate() {
        this.color = Color.DEFAULT;
    }

    private CellTemplate(Color color) {
        this.color = color;
    }

    private CellTemplate(CellTemplate original) {
        this.color = original.color;
        this.isCopy = true;
    }

    public CellTemplate copy() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot copy a singleton empty cell");
        }

        if (isCopy) {
            throw new IllegalStateException("Cannot make a new copy from a copy");
        }

        return new CellTemplate(this);
    }

    public boolean isEmpty() {
        return this == CellTemplate.EMPTY;
    }
}


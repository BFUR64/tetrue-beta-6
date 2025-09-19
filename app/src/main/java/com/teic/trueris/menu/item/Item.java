package com.teic.trueris.menu.item;

import java.io.IOException;

import com.teic.trueris.menu.MenuContext;

public abstract class Item {
    protected final String LABEL;
    protected final boolean SELECTABLE;

    protected Item(String label, boolean selectable) {
        this.LABEL = label;
        this.SELECTABLE = selectable;
    }

    public String getLabel() { return LABEL; }
    public boolean selectable() { return SELECTABLE; }
    
    public String getDisplayName() { return LABEL; }
    
    public boolean onSelect(MenuContext mc) throws IOException {
        if (!SELECTABLE) {
            throw new IllegalStateException("Tried to select a non-selectable item: " + LABEL);
        }

        // Default that tells the MenuManager to continue the loop 
        return true;
    }
}


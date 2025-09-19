package com.teic.trueris.menu.item;

import java.io.IOException;

import com.teic.trueris.menu.MenuContext;

public class ActionItem extends Item {
    private final Actionable action;

    public ActionItem(String label, Actionable action) {
        super(label, true);
        this.action = action;
    }

    @Override
    public boolean onSelect(MenuContext mc) throws IOException {
        return action.run();
    }
}


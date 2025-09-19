package com.teic.trueris.menu.item;

public class TextItem extends Item {
    public TextItem(String text) {
        super(text, false);
    }

    public static TextItem breakItem() {
        return new TextItem("");
    }
}


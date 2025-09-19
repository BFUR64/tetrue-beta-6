package com.teic.trueris.menu;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;

public record MenuContext (
        Terminal tm, 
        TextGraphics tg, 
        int colPos, 
        int rowPos) {}


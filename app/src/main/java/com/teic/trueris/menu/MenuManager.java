package com.teic.trueris.menu;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;
import com.teic.trueris.menu.item.Item;
import com.teic.trueris.sound.Sound;
import com.teic.trueris.sound.SoundManager;

public class MenuManager {
    private static final int ITEM_INDENT = 4;
    private static final int CUR_INDENT = 2;
    
    private final Terminal terminal;
    private final TextGraphics textGraphics;
    private final List<Item> menuList;
    private final String title;

    private final int minMenuSize = 0;
    private final int maxMenuSize;

    public MenuManager(
        Terminal terminal, 
        TextGraphics textGraphics, 
        List<Item> menuList, 
        String title
    ) {
        this.terminal = terminal;
        this.textGraphics = textGraphics;
        this.menuList = menuList;
        this.title = title;

        maxMenuSize = menuList.size() - 1;

        Logging.writeLog(
            LogType.INFO, 
            "Menu started: " + title
        );
    }

    public void run() throws IOException {
        terminal.resetColorAndSGR();
        terminal.clearScreen();

        int listIndex = minMenuSize;
        int prevListIndex = listIndex;

        drawMenu();

        while (!menuList.get(listIndex).selectable()) {
            listIndex++;
        }

        loop:
        while (true) {
            textGraphics.putString(
                CUR_INDENT, 
                prevListIndex + 1, 
                " "
            );
            textGraphics.putString(
                CUR_INDENT, 
                listIndex + 1, 
                ">"
            );

            terminal.flush();

            KeyStroke in = terminal.readInput();

            switch (in.getKeyType()) {
                case Escape -> {
                    Logging.writeLog(
                        LogType.INFO, 
                        "Menu exit: " + title
                    );
                    SoundManager.playSFX(Sound.MENU_BACK);
                    break loop;
                }
                case ArrowUp -> {
                    SoundManager.playSFX(Sound.MENU_MOVE);
                    prevListIndex = listIndex;
                    do {
                        listIndex--;

                        if (listIndex < minMenuSize) {
                            listIndex = maxMenuSize;
                        }
                    }
                    while (!menuList.get(listIndex) 
                            .selectable()
                    );
                }
                case ArrowDown -> {
                    SoundManager.playSFX(Sound.MENU_MOVE);
                    prevListIndex = listIndex;
                    do {
                        listIndex++;

                        if (listIndex > maxMenuSize) {
                            listIndex = minMenuSize;
                        }
                    }
                    while (!menuList.get(listIndex)
                            .selectable()
                    );
                }
                case Enter -> {
                    Logging.writeLog(
                        LogType.DEBUG, 
                        "Menu selection: "
                        + menuList.get(listIndex)
                        .getLabel()
                    );

                    SoundManager.playSFX(
                        Sound.MENU_SELECT
                    );
                    MenuContext menuContext = 
                        new MenuContext(
                            terminal, 
                            textGraphics, 
                            ITEM_INDENT, 
                            listIndex + 1
                        );
                    
                    if(!menuList.get(listIndex)
                                .onSelect(menuContext)
                    ) {
                        break loop;
                    }

                    terminal.resetColorAndSGR();
                    terminal.clearScreen();
                    
                    drawMenu();
                }
                default -> {}
            }
        }
    }

    private void drawMenu() throws IOException {
        centerText(0, title);
    
        for (int i = 0; i <= maxMenuSize; i++) {
            textGraphics.putString(
                ITEM_INDENT, 
                i + 1, 
                menuList.get(i).getDisplayName()
            );
        }
    }

    private void centerText(int rowPos, String text) 
        throws IOException {
        TerminalSize tSize = terminal.getTerminalSize();
        int tCol = tSize.getColumns();

        int colPos = (tCol / 2) - (text.length() / 2);

        textGraphics.putString(colPos, rowPos, text);
    }
}


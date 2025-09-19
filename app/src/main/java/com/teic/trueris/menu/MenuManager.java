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
    
    private final Terminal tm;
    private final TextGraphics tg;
    private final List<Item> menuList;
    private final String title;

    private final int MIN_MENU_SIZE = 0;
    private final int MAX_MENU_SIZE;

    public MenuManager(Terminal tm, TextGraphics tg, List<Item> menuList, String title) {
        this.tm = tm;
        this.tg = tg;
        this.menuList = menuList;
        this.title = title;

        MAX_MENU_SIZE = menuList.size() - 1;

        Logging.writeLog(LogType.INFO, 
                "Menu started: " + title);
    }

    public void run() throws IOException {
        tm.resetColorAndSGR();
        tm.clearScreen();

        int listIndex = MIN_MENU_SIZE;
        int prevListIndex = listIndex;

        drawMenu();

        while (!menuList.get(listIndex).selectable()) {
            listIndex++;
        }

        loop:
        while (true) {
            tg.putString(CUR_INDENT, prevListIndex + 1, " ");
            tg.putString(CUR_INDENT, listIndex + 1, ">");

            tm.flush();

            KeyStroke in = tm.readInput();

            switch (in.getKeyType()) {
                case Escape -> {
                    Logging.writeLog(LogType.INFO, 
                            "Menu exit: " + title);
                    SoundManager.playSFX(Sound.MENU_BACK);
                    break loop;
                }
                case ArrowUp -> {
                    SoundManager.playSFX(Sound.MENU_MOVE);
                    prevListIndex = listIndex;
                    do {
                        listIndex--;

                        if (listIndex < MIN_MENU_SIZE)
                            listIndex = MAX_MENU_SIZE;
                    }
                    while (!menuList.get(listIndex) 
                            .selectable());
                }
                case ArrowDown -> {
                    SoundManager.playSFX(Sound.MENU_MOVE);
                    prevListIndex = listIndex;
                    do {
                        listIndex++;

                        if (listIndex > MAX_MENU_SIZE)
                            listIndex = MIN_MENU_SIZE;
                    }
                    while (!menuList.get(listIndex)
                            .selectable());
                }
                case Enter -> {
                    Logging.writeLog(
                            LogType.DEBUG, 
                            "Menu selection: "
                            + menuList.get(listIndex)
                            .getLabel());

                    SoundManager.playSFX(Sound.MENU_SELECT);
                    MenuContext mc = new MenuContext(
                            tm, tg, ITEM_INDENT, 
                            listIndex + 1);
                    
                    if(!menuList.get(listIndex).onSelect(mc))
                        break loop;

                    tm.resetColorAndSGR();
                    tm.clearScreen();
                    
                    drawMenu();
                }
                default -> {}
            }
        }
    }

    private void drawMenu() throws IOException {
        centerText(0, title);
    
        for (int i = 0; i <= MAX_MENU_SIZE; i++) {
            tg.putString(ITEM_INDENT, i + 1, 
                    menuList.get(i).getDisplayName());
        }
    }

    private void centerText(int rowPos, String text) throws IOException {
        TerminalSize tSize = tm.getTerminalSize();
        int tCol = tSize.getColumns();

        int colPos = (tCol / 2) - (text.length() / 2);

        tg.putString(colPos, rowPos, text);
    }
}


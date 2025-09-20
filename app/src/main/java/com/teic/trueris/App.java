package com.teic.trueris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.game.GameLoop;
import com.teic.trueris.menu.MenuManager;
import com.teic.trueris.menu.item.ActionItem;
import com.teic.trueris.menu.item.EditableItem;
import com.teic.trueris.menu.item.Item;
import com.teic.trueris.menu.item.TextItem;
import com.teic.trueris.sound.SoundManager;

public class App {
    private final Terminal tm;
    private final TextGraphics tg;
    private final Config config;

    public static void main(String[] args) {
        Logging.writeBreakLine();
        Logging.writeLog(LogType.INFO, "Session start");
        
        Terminal tm = null;

        try {
            tm = new DefaultTerminalFactory()
                .createTerminal();
            tm.setCursorVisible(false);
            TextGraphics tg = tm.newTextGraphics();

            tm.enterPrivateMode();
            
            App app = new App(tm, tg);
            
            app.run();

            tm.exitPrivateMode();
        }
        catch (Exception er1) {
            if (tm != null) {
                try {
                    tm.exitPrivateMode();
                }
                catch (IOException er2) {
                    Logging.writeStackTrace(
                            LogType.ERROR, er2);
                }
            }

            Logging.writeStackTrace(LogType.ERROR, er1);
        } finally {
            Logging.writeLog(LogType.INFO, "Session end");
            Logging.writeBreakLine();
        }
    }

    private App(Terminal tm, TextGraphics tg) throws IOException {
        Logging.writeLog(LogType.INFO, "App Start");

        this.tm = tm;
        this.tg = tg;

        tg.putString(0, 0, "Loading...");
        tg.putString(0, 1, "Testing");

        this.config = Config.readConfig();
        Logging.writeLog(LogType.INFO, "Config loaded");

        SoundManager.initialize();
        Logging.writeLog(LogType.INFO, "Sounds loaded");
    }

    private void run() throws IOException {
        MenuManager menuManager = 
            new MenuManager(
                    tm, 
                    tg, 
                    buildMainMenu(), 
                    "<<< TetrueBeta 6 >>>");

        menuManager.run();
    }

private List<Item> buildMainMenu() {
        List<Item> menuList = new ArrayList<>();

        menuList.add(new ActionItem(
                    "Start Game", 
                    () -> {
                        GameLoop gl = new GameLoop(tm, tg, config);
                        gl.run();
                        return true;
                    }));
        menuList.add(new ActionItem(
                    "Settings",
                    () -> {
                        MenuManager menuManager = 
                            new MenuManager(
                                    tm, 
                                    tg, 
                                    buildSettingsMenu(), 
                                    "<<< Settings >>>");

                        menuManager.run();

                        return true;
                    }));
        menuList.add(new ActionItem(
                    "About", 
                    () -> {
                        MenuManager menuManager =
                            new MenuManager(
                                    tm,
                                    tg,
                                    buildAboutMenu(),
                                    "<<< About >>>");

                        menuManager.run();
                        
                        return true;
                    }));
        menuList.add(new ActionItem(
                    "Exit", 
                    () -> { return false; }
                    ));
        menuList.add(TextItem.breakItem());
        menuList.add(new TextItem("  [TIP] Use up and down arrows to move!"));
        menuList.add(new TextItem("  [TIP] Press Enter to enter the menus!"));
        
        return menuList;
    }

    private List<Item> buildSettingsMenu() {
        List<Item> menuList = new ArrayList<>();

        menuList.add(new EditableItem(
                    "Target FPS", 
                    () -> config.getTargetFps(), 
                    v -> config.setTargetFps(v)
                    ));
        menuList.add(new EditableItem(
                    "Gravity", 
                    () -> config.getGravity(), 
                    v -> config.setGravity(v)
                    ));
        menuList.add(new EditableItem(
                    "Height", 
                    () -> config.getHeight(), 
                    v -> config.setHeight(v)
                    ));
        menuList.add(new EditableItem(
                    "Width", 
                    () -> config.getWidth(), 
                    v -> config.setWidth(v)
                    ));
        menuList.add(TextItem.breakItem());
        menuList.add(new ActionItem(
                    "[ Revert To Defaults ]", 
                    () -> {
                        config.setDefault();
                        config.writeConfig();
                        tm.clearScreen();
                        return true;
                    }));
        menuList.add(new ActionItem(
                    "[ Return ]", 
                    () -> {
                        return false;
                    }));
        menuList.add(TextItem.breakItem());
        menuList.add(new TextItem("  [TIP] Press Enter to edit!"));
        
        return menuList;
    }

    private List<Item> buildAboutMenu() {
        List<Item> menuList = new ArrayList<>();
        
        menuList.add(TextItem.breakItem());
        menuList.add(new TextItem("This is my 6th attempt at making a Tet*** clone!"));
        menuList.add(TextItem.breakItem());
        menuList.add(new ActionItem(
                    "[ Return ]",
                    () -> { return false; }
                    ));

        return menuList;
    }
}


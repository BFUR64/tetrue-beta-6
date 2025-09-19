package com.teic.trueris.game;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.Config;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;
import com.teic.trueris.sound.Sound;
import com.teic.trueris.sound.SoundManager;

public class GameLoop {
    private static final int NSEC = 1_000_000_000;
    private static final int MSEC = 1_000_000;
    private static final int SLEEP_THRESHOLD = 2_000_000;

    private final Terminal tm;
    private final TextGraphics tg;
    private final Config cfg;

    private boolean running;
    private final int targetFps;
    private final int nsPerFrame;

    public GameLoop(Terminal tm, TextGraphics tg, Config cfg) {
        this.tm = tm;
        this.tg = tg;
        this.cfg = cfg;

        this.targetFps = (int) cfg.getTargetFps();
        this.nsPerFrame = NSEC / targetFps;
    }

    public void run() throws IOException {
        Logging.writeLog(LogType.INFO, "Game started");
        
        tm.clearScreen();
        tm.flush();

        SoundManager sManager = new SoundManager();
        sManager.playMusic(Sound.BG_MUSIC);

        Renderer renderer = new Renderer(tm, tg, cfg);
        renderer.renderBorder();

        running = true;

        long remaining = 0;
        long timer = 0;

        while (running) {
            long frameStart = System.nanoTime();

            calculations();

            long delta = System.nanoTime() - frameStart;
            remaining = nsPerFrame - delta;
            
            if (remaining >= SLEEP_THRESHOLD) {
                try {
                    Thread.sleep(remaining / MSEC);
                }
                catch (InterruptedException e) {
                    Logging.writeStackTrace(LogType.ERROR, e);
                }
            }

            while (
                    System.nanoTime() - frameStart 
                    < nsPerFrame
                    ) {}

            long frameTime = System.nanoTime() - frameStart;
            timer += frameTime;
        }

        sManager.stopMusic();
        
        tm.clearScreen();
        
        Logging.writeLog(LogType.INFO, "Game exit");
    }

    private void calculations() throws IOException {
        handleKey(tm.pollInput());
    }

    private void handleKey(KeyStroke key) {
        if (key == null) return;

        switch (key.getKeyType()) {
            case Escape -> { running = false; }
            default -> { return; }
        }
    }
}


package com.teic.trueris.game;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.Config;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;

public class GameLoop {
    private static final int NSEC = 1_000_000_000;
    private static final int MSEC = 1_000_000;
    private static final int SLEEP_THRESHOLD = MSEC * 2;

    private final Terminal terminal;
    private final Renderer renderer;
    private final GridManager gridManager;

    private boolean running;
    private final int targetFps;
    private final int nsPerFrame;

    public GameLoop(Terminal terminal, Renderer renderer, GridManager gridManager, Config config) {
        this.terminal = terminal;
        this.renderer = renderer;
        this.gridManager = gridManager;

        this.targetFps = (int) config.getTargetFps();
        this.nsPerFrame = NSEC / targetFps;
    }

    public void run() throws IOException {
        Logging.writeLog(LogType.INFO, "Game started");

        terminal.clearScreen();

        renderer.renderBorder();
        renderer.updateScreen();

        terminal.flush();

        running = true;
        while (running) {
            long frameStart = System.nanoTime();
            
            handleGameState(terminal.pollInput());
            
            long delta = System.nanoTime() - frameStart;
            long remaining = nsPerFrame - delta;

            if (remaining >= SLEEP_THRESHOLD) {
                try {
                    Thread.sleep(remaining / MSEC);
                }
                catch (InterruptedException e) {
                    Logging.writeStackTrace(LogType.ERROR, e);
                }
            }

            while (System.nanoTime() - frameStart < nsPerFrame) {}

            // long frameTime = System.nanoTime() - frameStart;
        }
    }

    private void handleGameState(KeyStroke key) {
        if (key == null) {
            return;
        }

        switch (key.getKeyType()) {
            case Escape -> {
                running = false;
            }

            case ArrowUp -> {
                gridManager.dropBlock();
                renderer.updateScreen();
            }

            case ArrowDown -> {
                gridManager.moveBlockDown();
                renderer.updateScreen();
            }

            case ArrowLeft -> {
                gridManager.moveBlockLeft();
                renderer.updateScreen();
            }

            case ArrowRight -> {
                gridManager.moveBlockRight();
                renderer.updateScreen();
            }

            case Home -> {
                gridManager.rotateBlockLeft();
                renderer.updateScreen();
            }

            case End -> {
                gridManager.rotateBlockRight();
                renderer.updateScreen();
            }

            default -> {
                return;
            }
        }
    }
}


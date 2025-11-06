package com.teic.trueris.game;

import java.io.IOException;

import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.Config;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;

public class GameLoop {
    private static final int NSEC = 1_000_000_000;
    private static final int MSEC = 1_000_000;
    private static final int SLEEP_THRESHOLD = MSEC * 2;

    private final Terminal terminal;

    private boolean running;
    private final int targetFps;
    private final int nsPerFrame;

    public GameLoop(Terminal terminal, Config config) {
        this.terminal = terminal;

        this.targetFps = (int) config.getTargetFps();
        this.nsPerFrame = NSEC / targetFps;
    }

    public void run() throws IOException {
        Logging.writeLog(LogType.INFO, "Game started");

        terminal.clearScreen();
        terminal.flush();

        running = true;
        while (running) {
            long frameStart = System.nanoTime();

            // Do Calculations Here

            // End Calculations

            long delta = System.nanoTime() - frameStart;
            long remaining = nsPerFrame - delta;

            while (remaining >= SLEEP_THRESHOLD) {
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
}


package com.teic.trueris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class Config {
    private static final String CONFIG_NAME = "config.json";
    private static final double DEF_FPS = 60;
    private static final double DEF_GRAVITY = 1.0;
    private static final double DEF_HEIGHT = 20;
    private static final double DEF_WIDTH = 10;

    private static final double MIN_FPS = 30;
    private static final double MAX_FPS = 120;
    
    private static final double MIN_GRAVITY = 0.5;
    private static final double MAX_GRAVITY = 20.0;

    private static final double MIN_HEIGHT = 10;
    private static final double MAX_HEIGHT = 30;

    private static final double MIN_WIDTH = 10;
    private static final double MAX_WIDTH = 30;

    private double targetFps;
    private double gravity;
    private double height;
    private double width;

    public static Config readConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(
                PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(
                PropertyAccessor.FIELD, Visibility.ANY
                );

        Config config;

        try {
            Logging.writeLog(LogType.INFO, "Reading config");
            config = mapper
                .readValue(
                        new File(CONFIG_NAME), 
                        Config.class);
        }
        catch (FileNotFoundException 
                | JsonParseException 
                | MismatchedInputException e) {
            Logging.writeStackTrace(LogType.WARN, e);
            
            config = new Config();
            config.setDefault();
            config.writeConfig();
            
            return config;
        }

        if (!config.ensureValidSettings())
            config.writeConfig();

        return config;
    }

    public void writeConfig() {
        ensureValidSettings();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(CONFIG_NAME), this);
            
            Logging.writeLog(LogType.INFO, "Config written");
        }
        catch (IOException e) {
            Logging.writeStackTrace(LogType.ERROR, e);
        }
    }

    public void setDefault() {
        this.targetFps = DEF_FPS;
        this.gravity = DEF_GRAVITY;
        this.height = DEF_HEIGHT;
        this.width = DEF_WIDTH;
    }

    private boolean ensureValidSettings() {
        int fixes = 0;

        if (!isTargetFpsValid(targetFps)) {
            targetFps = DEF_FPS;
            fixes++;
        }
        if (!isGravityValid(gravity)) {
            gravity = DEF_GRAVITY;
            fixes++;
        }
        if (!isHeightValid(height)) {
            height = DEF_HEIGHT;
            fixes++;
        }
        if (!isWidthValid(width)) {
            width = DEF_WIDTH;
            fixes++;
        }
        
        if (fixes > 0) {
            Logging.writeLog(LogType.INFO, "Settings fixed: " + fixes);
            return false;
        }

        return true;
    }

    private boolean isTargetFpsValid(double value) {
        return isValueValid(value, MIN_FPS, MAX_FPS);
    }

    private boolean isGravityValid(double value) {
        return isValueValid(value, MIN_GRAVITY, MAX_GRAVITY);
    }

    private boolean isHeightValid(double value) {
        return isValueValid(value, MIN_HEIGHT, MAX_HEIGHT);
    }

    private boolean isWidthValid(double value) {
        return isValueValid(value, MIN_WIDTH, MAX_WIDTH);
    }

    private boolean isValueValid(double value, double min, double max) {
        if (value < min || value > max)
            return false;

        return true;
    }

    public double getTargetFps() { return targetFps; }
    public double getGravity() { return gravity; }
    public double getHeight() { return height; }
    public double getWidth() { return width; }

    public boolean setTargetFps(double value) {
        boolean isValid = isTargetFpsValid(value);

        if (isValid) {
            this.targetFps = value;
            writeConfig();
        }
        
        return isValid;
    }

    public boolean setGravity(double value) {
        boolean isValid = isGravityValid(value);

        if (isValid) {
            this.gravity = value;
            writeConfig();
        }

        return isValid;
    }

    public boolean setHeight(double value) {
        boolean isValid = isHeightValid(value);

        if (isValid) {
            this.height = value;
            writeConfig();
        }

        return isValid;
    }

    public boolean setWidth(double value) {
        boolean isValid = isWidthValid(value);

        if (isValid) {
            this.width = value;
            writeConfig();
        }

        return isValid;
    }
}


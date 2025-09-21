package com.teic.trueris;

public enum LogType {
    DEBUG("[ DEBUG ]", 0),
    INFO("[ INFO ]", 1),
    WARN("[ WARN ]", 2),
    ERROR("[ ERROR ]", 3);

    public final String label;
    public final int severity;

    private LogType(String label, int logLevel) {
        this.label = label;
        this.severity = logLevel;
    }
}


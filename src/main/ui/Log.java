package ui;

import model.RunningLog;

public abstract class Log {
    private RunningLog runningLog = new RunningLog();

    public RunningLog getRunningLog() {
        return runningLog;
    }
}

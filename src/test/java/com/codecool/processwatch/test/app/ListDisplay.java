package com.codecool.processwatch.test.app;

import java.util.List;

import com.codecool.processwatch.domain.ProcessDisplay;
import com.codecool.processwatch.domain.Process;

public class ListDisplay extends ProcessDisplay {
    private final List<Process> processList;

    public ListDisplay(List<Process> processList) {
        this.processList = processList;
    }

    @Override
    protected void clear() {
        processList.clear();
    }

    protected void addProcess(Process process) {
        processList.add(process);
    }
}

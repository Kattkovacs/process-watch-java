package com.codecool.processwatch.test.app;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Stream;

import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.Process;

public class ListProcessSource implements ProcessSource {
    private final List<Process> processes = new LinkedList<>();

    public void addAll(Process... processes) {
        this.processes.addAll(Arrays.asList(processes));
    }

    @Override
    public Stream<Process> getProcesses() {
        return processes.stream();
    }
}

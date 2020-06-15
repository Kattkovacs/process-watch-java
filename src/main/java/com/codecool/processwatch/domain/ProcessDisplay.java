package com.codecool.processwatch.domain;

import java.util.stream.Stream;

/**
 * Display a list of processes.  The display is ordered.
 */
public abstract class ProcessDisplay {
    /**
     * Display the current processes.
     * <p>
     * Clears the display before getting the new processes.
     *
     * @param processes a stream of a snapshot of current processes.
     */
    public final void display(Stream<Process> processes){
        clear();
        processes.forEach(this::addProcess);
    }

    protected abstract void clear();

    protected abstract void addProcess(Process process);
}

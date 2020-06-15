package com.codecool.processwatch.domain;

import java.util.stream.Stream;

/**
 * Represents a source that can provide a snapshot of the current processes.
 */
public interface ProcessSource {
    /**
     * Get a snapshot of the current processes.
     *
     * @return a {@code Stream} of processes.
     */
    Stream<Process> getProcesses();
}

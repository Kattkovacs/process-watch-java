package com.codecool.processwatch.domain;

import java.util.stream.Stream;

/**
 * A query on the currentprocesses.
 * It can be used to filter them.
 */
public interface Query {
    /**
     * Apply the logic in the query to a stream of processes.
     *
     * @param input a {@code Stream} of processes.
     *
     * @return a filtered {@code Stream} of processes.
     */
    Stream<Process> run(Stream<Process> input);
}

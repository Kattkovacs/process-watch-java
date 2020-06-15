package com.codecool.processwatch.queries;

import com.codecool.processwatch.domain.Query;
import com.codecool.processwatch.domain.Process;

import java.util.stream.Stream;

/**
 * This is the identity query.  It selects everything from its source.
 */
public class SelectAll implements Query {
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Process> run(Stream<Process> input) {
        return input;
    }
}

package com.codecool.processwatch.os;

import java.util.stream.Stream;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.User;

/**
 * A process source using the Java {@code ProcessHandle} API to retrieve information
 * about the current processes.
 */
public class OsProcessSource implements ProcessSource {
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Process> getProcesses() {
        return Stream.of(new Process(1,  1, new User("root"), "init", new String[0]),
                         new Process(42, 1, new User("Codecooler"), "processWatch", new String[] {"--cool=code", "-v"}));
    }
}

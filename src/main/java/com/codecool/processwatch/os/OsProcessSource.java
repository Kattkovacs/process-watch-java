package com.codecool.processwatch.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

        List<ProcessHandle> allProcesses = ProcessHandle.allProcesses().collect(Collectors.toList());
        List<Process> processes = new ArrayList<>();
        for (ProcessHandle process : allProcesses) {

            Optional<ProcessHandle> parent = process.parent();
            long parentId = 1;
            if (parent.isPresent()) {
                parentId = parent.get().pid();
            }

            Optional<String> commands = process.info().command();
            String strCommand = commands.isPresent() ? commands.get() : new String("N/A");

            Optional<String[]> arguments = process.info().arguments();
            String[] argsArray = arguments.isPresent() ? arguments.get() : new String[]{"N/A"};

            processes.add(new Process(process.pid(), parentId, new User(process.info().user().get()), strCommand, argsArray));
        }

        return processes.stream();

    }
}

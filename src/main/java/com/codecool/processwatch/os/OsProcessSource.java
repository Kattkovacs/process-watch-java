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
    private static List<Process> processes = new ArrayList<>();

    public OsProcessSource() {
        setProcesses();
    }

    /**
     * This method gets information about the current processes (by using Java ProcessHandle)
     * and use the constructor of Process class to create new Process object for every current
     * process.
     * In addition the method calls addProcessToList() method to make a collection from the
     * created Processes.
     */
    public static void setProcesses() {
        List<ProcessHandle> allProcesses = ProcessHandle.allProcesses().collect(Collectors.toList());
        System.out.println("allproc size" + allProcesses.size());
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
            Process processToAdd = new Process(process.pid(), parentId, new User(process.info().user().get()), strCommand, argsArray);
            addProcessToList(processToAdd);
        }
    }

    /**
     * Add Process object to the collection called: 'processes'
     * @param processToAdd the Process object to add to the collection
     */
    public static void addProcessToList(Process processToAdd) {
        processes.add(processToAdd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Process> getProcesses() {
        return processes.stream();

    }
}

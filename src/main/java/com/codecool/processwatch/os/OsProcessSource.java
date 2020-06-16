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
            Optional<String[]> arguments = process.info().arguments();
            String[] argsArray = arguments.isPresent() ? arguments.get() : new String[0];

            processes.add(new Process(process.pid(), parentId, new User(process.info().user().toString()),process.info().command().toString(),
                    argsArray));
        }

//
//        ProcessHandle processHandle = ProcessHandle.current();
//        ProcessHandle.Info processInfo = processHandle.info();
////        Stream<Process> row = null;
//        for (int i = 0; i < allProcesses.length; i++) {
//            processes.add(new Process(Long.parseLong(allProcesses[i].toString()), 1,
//                    new User(processInfo.user().toString()), processInfo.command().toString(),
//                    new String[]{processInfo.arguments().toString()}));
//        }
//        Stream<Process> stream = Arrays.stream(processes);
        return processes.stream();


//        Stream<ProcessHandle> allProcesses = ProcessHandle.allProcesses();
//        words.stream().filter(word -> word.length() > 6).forEach(System.out::println);
//        return Stream.of(new Process(1,  1, new User("root"), "init", new String[0]),
//                         new Process(42, 1, new User("Codecooler"), "processWatch", new String[] {"--cool=code", "-v"}));
    }
}

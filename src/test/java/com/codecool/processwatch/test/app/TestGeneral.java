package com.codecool.processwatch.test.app;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.codecool.processwatch.domain.ProcessWatchApp;
import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.User;

public class TestGeneral {
    private static final User     DEFAULT_USER = new User("codecooler");
    private static final String   DEFUALT_NAME = "java";
    private static final String[] DEFAULT_ARGS = {"-jar", "super-app.jar"};

    private ProcessWatchApp app;
    private List<Process> results;
    private ListProcessSource source;

    @BeforeEach
    public void setUp() {
        results = new LinkedList<>();
        source = new ListProcessSource();
        app = new App(source, results);
    }

    @Test
    public void noProcesses() {
        app.refresh();
        assertEquals(Collections.emptyList(), results);
    }

    @Test
    public void oneProcess() {
        source.addAll(new Process(23L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS));
        app.refresh();
        assertIterableEquals(Collections.singletonList(new Process(23L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS)), results);
    }

    @Test
    public void twoProcessesPreservingOrder() {
        source.addAll(new Process(42L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS),
                      new Process(51L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS));
        app.refresh();
        var expected = List.of(new Process(42L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS),
                               new Process(51L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS));
        assertIterableEquals(expected, results);
    }

    @Test
    public void refreshStartsFromCleanSlate() {
        source.addAll(new Process(42L, 1, DEFAULT_USER, DEFUALT_NAME, DEFAULT_ARGS));
        app.refresh();
        app.refresh();
        assertEquals(1, results.size());
    }
}

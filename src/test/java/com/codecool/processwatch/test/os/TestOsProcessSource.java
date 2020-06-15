package com.codecool.processwatch.test.os;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.codecool.processwatch.os.OsProcessSource;

public class TestOsProcessSource {
    private OsProcessSource processAccessor;

    @BeforeEach
    public void setUp() {
        processAccessor = new OsProcessSource();
    }

    @Test
    public void testNoIllegalAccess() {
        assertDoesNotThrow(processAccessor::getProcesses);
    }

    @Test
    public void testProcessesAreFound() {
        var processes = processAccessor
            .getProcesses();

        assertTrue(processes.count() > 0);
    }

    @Test
    public void testTheresAnInit() {
        var processes = processAccessor.getProcesses();
        var rootProcOpt = processes
            .filter(proc -> Long.valueOf(1).equals(proc.getPid()))
            .findFirst();

        if (rootProcOpt.isPresent()) {
            var proc = rootProcOpt.get();
            assertEquals("root", proc.getUserName());
        } else {
            fail("No process with PID 1 found");
        }
    }
}

package com.codecool.processwatch.test.app;

import java.util.List;

import com.codecool.processwatch.domain.ProcessWatchApp;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.queries.SelectAll;

public class App extends ProcessWatchApp {
    public App(ProcessSource processSource, List<Process> dummyDisplay) {
        super(processSource, new ListDisplay(dummyDisplay), new SelectAll());
    }
}

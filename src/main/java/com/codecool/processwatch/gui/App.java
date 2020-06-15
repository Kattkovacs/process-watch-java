package com.codecool.processwatch.gui;

import javafx.collections.ObservableList;

import com.codecool.processwatch.domain.ProcessWatchApp;
import com.codecool.processwatch.os.OsProcessSource;
import com.codecool.processwatch.queries.SelectAll;

/**
 * An application using a real process source and a JavaFx process display.
 */
public class App extends ProcessWatchApp {
    /**
     * Create an instance of the application.
     *
     * @param displayList a list of process view objects. This list is shared between
     *                    the application and the application window.
     */
    public App(ObservableList<ProcessView> displayList) {
        super(new OsProcessSource(), new GuiProcessDisplay(displayList), new SelectAll());
    }
}

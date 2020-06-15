package com.codecool.processwatch.gui;

import javafx.collections.ObservableList;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessDisplay;

/**
 * A process display that interacts with a JavaFX gui via a shared
 * observable list instance.
 */
public class GuiProcessDisplay extends ProcessDisplay  {
    private final ObservableList<ProcessView> displayList;

    /**
     * Create the display logic.
     *
     * @param displayList an observable list manipulated by this class and
     *                    observed by the application window.
     */
    public GuiProcessDisplay(ObservableList<ProcessView> displayList) {
        this.displayList = displayList;
    }

    @Override
    protected void clear() {
        displayList.clear();
    }

    @Override
    protected void addProcess(Process proc) {
        displayList.add(new ProcessView(proc));
    }
}

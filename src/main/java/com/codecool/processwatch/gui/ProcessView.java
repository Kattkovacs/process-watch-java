package com.codecool.processwatch.gui;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.codecool.processwatch.domain.Process;

/**
 * A view representing a process.
 * <p>
 * It mirrors the fields of a process as javaFX properties.
 */
public class ProcessView {
    public ProcessView(Process process) {
        this.pid = new SimpleLongProperty(process.getPid());
        this.parentPid = new SimpleLongProperty(process.getParentPid());
        this.userName = new SimpleStringProperty(process.getUserName());
        this.processName = new SimpleStringProperty(process.getName());
        this.args = new SimpleStringProperty(merge(process.getArgs()));
    }

    private LongProperty pid;
    public void setPid(Long value) { pidProperty().set(value); }
    public Long getPid() { return pidProperty().get(); }
    public LongProperty pidProperty() {
        if (pid == null) {
            pid = new SimpleLongProperty(this, "pid");
        }
        return pid;
    }

    private LongProperty parentPid;
    public void setParentPid(Long value) { parentPidProperty().set(value); }
    public Long getParentPid() { return parentPidProperty().get(); }
    public LongProperty parentPidProperty() {
        if (parentPid == null) {
            parentPid = new SimpleLongProperty(this, "parentPid");
        }
        return parentPid;
    }

    private StringProperty userName;
    public void setUserName(String value) { userNameProperty().set(value); }
    public String getUserName() { return userNameProperty().get(); }
    public StringProperty userNameProperty() {
        if (userName == null) userName = new SimpleStringProperty(this, "userName");
        return userName;
    }

    private StringProperty processName;
    public void setProcessName(String value) { processNameProperty().set(value); }
    public String getProcessName() { return processNameProperty().get(); }
    public StringProperty processNameProperty() {
        if (processName == null) processName = new SimpleStringProperty(this, "processName");
        return processName;
    }

    private StringProperty args;
    public void setArgs(String value) { argsProperty().set(value); }
    public String getArgs() { return argsProperty().get(); }
    public StringProperty argsProperty() {
        if (args == null) args = new SimpleStringProperty(this, "args");
        return args;
    }


    private static String merge(String[] strings) {
        return String.join(" ", strings);
    }
}

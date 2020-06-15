package com.codecool.processwatch.domain;

import java.util.Arrays;

/**
 * A class representing a displayable and queryable process.
 */
public class Process {
    private long pid;
    private long parentPid;
    private User user;
    private String name;
    private String[] args;

    /**
     * Create an object representing a process.
     *
     * @param pid       the process ID of the process.
     * @param parentPid the pid of the processes parent.
     * @param user      the user who is the owner of the process
     * @param name      a name for the process. Usually the name of the command
     *                  that started the process.
     * @param args      the arguments of that were given to the process if any.
     */
    public Process(long pid, long parentPid, User user, String name, String[] args) {
        this.pid = pid;
        this.parentPid = parentPid;
        this.user = user;
        this.name = name;
        this.args = args;
    }

    /**
     * Get the process ID that belongs to the process.
     *
     * @return a long representing the process ID.
     */
    public long getPid() {
        return pid;
    }

    /**
     * Get the Process ID of the process' parent process.
     *
     * @return a long representing the process ID.
     */
    public long getParentPid() {
        return parentPid;
    }

    /**
     * Get the user name of the process owner.
     *
     * @return a String representing the username.
     */
    public String getUserName() {
        return user.getName();
    }

    /**
     * Get the name describing the process.
     * <p>
     * Usually the name of the executable.
     *
     * @return a String representing the process.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the arguments passed to the executable.
     *
     * @return a String[] of parameters
     */
    public String[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    /**
     * Get a string representation of the process.
     * <p>
     * It displays the pid, ppid and user.
     *
     * @return a String representing the process.
     */
    @Override
    public String toString() {
        return String.format("Process {pid = %6d, parentPid = %6d, user = %s}",
                             pid, parentPid, user.getName());
    }

    /**
     * Equality check with other processes.
     * <p>
     * Uses the process ID for identity.
     * If the other process has the same pid then it will be considered equal.
     *
     * @param o the other process to compare for equality.
     *
     * @return {@code true} if {@code o} has the same pid.
     */
    @Override
    public boolean equals(Object o) {
        var clazz = getClass();
        if (!clazz.isInstance(o)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        var other = clazz.cast(o);

        return this.pid == other.pid;
    }

    /**
     * Generate a hash value for the process.
     * <p>
     * The value of the hash is only based on the process ID.
     * Other values are not considered.
     *
     * @return the hash of the pid.
     */
    @Override
    public int hashCode() {
        var objPid = Long.valueOf(pid);
        return objPid.hashCode();
    }
}

package com.codecool.processwatch.domain;

/**
 * A user who can own a process.
 */
public class User {
    private final String name;

    /**
     * Create an instance for the owner of a process.
     *
     * @param name a name representing the user.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Get the name of the user.
     *
     * @return a string for the name of the user.
     */
    public String getName() {
        return name;
    }
}

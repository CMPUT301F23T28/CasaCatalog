package com.cmput301f23t28.casacatalog.models;

/**
 * Represents a user with a name property.
 */
public class User {
    private String name;

    /**
     * Constructor for creating a new User with a given name.
     * @param name A string representing the user's name.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the user.
     * @return A string representing the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the name of the user.
     * @param name A string containing the new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }
}

package com.cmput301f23t28.casacatalog.models;

/**
 * Represents a user with a name property.
 */
public class User {
    private String deviceId;
    private String name;

    /**
     * Constructor for creating a new User with a given name.
     * @param deviceId A string representing the user's device ID.
     * @param name A string representing the user's name.
     */
    public User(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }

    /**
     * Retrieves the device ID of the user.
     * @return A string representing the user's device ID.
     */
    public String getDeviceId(){ return deviceId; }

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

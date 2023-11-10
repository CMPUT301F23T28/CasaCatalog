package com.cmput301f23t28.casacatalog.models;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.provider.Settings;

/**
 * Represents a user with a name property.
 */
public class User {
    private String deviceId;
    private String name;

    /**
     * Constructor for creating a new User with a given name.
     * @param name A string representing the user's name.
     * Retrieves the name of the user.
     * @return A string representing the user's name.
     */
    public User(Context context, String name) {
        this.deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.name = name;
    }
    public User(String name) {

    }
    public String getDeviceId(){ return deviceId; }

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

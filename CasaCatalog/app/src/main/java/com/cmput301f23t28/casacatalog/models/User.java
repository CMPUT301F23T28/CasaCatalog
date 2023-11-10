package com.cmput301f23t28.casacatalog.models;


/**
 * Represents a user with a name property.
 */
import static java.security.AccessController.getContext;

import android.content.Context;
import android.provider.Settings;

public class User {
    private String deviceId;
    private String name;

    /**
     * Retrieves the name of the user.
     * @return A string representing the user's name.
     */
    public User(Context context, String name) {
        this.deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.name = name;
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

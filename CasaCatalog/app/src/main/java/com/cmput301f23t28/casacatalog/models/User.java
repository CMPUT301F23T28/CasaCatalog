package com.cmput301f23t28.casacatalog.models;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.provider.Settings;

public class User {
    private String deviceId;
    private String name;

    public User(Context context, String name) {
        this.deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.name = name;
    }

    public String getDeviceId(){ return deviceId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

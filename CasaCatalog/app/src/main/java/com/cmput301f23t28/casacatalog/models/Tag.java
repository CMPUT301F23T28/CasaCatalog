package com.cmput301f23t28.casacatalog.models;

public class Tag {

    private String name;
    private int usages;

    public Tag(String name) {
        this.name = name;
        this.usages = 0;
    }

    public String getName() {
        return this.name;
    }

    // This is a bit out of the scope of the user story
    // so it will be unused for now. QoL feature.
    public int getUsages(){
        return this.usages;
    }
}

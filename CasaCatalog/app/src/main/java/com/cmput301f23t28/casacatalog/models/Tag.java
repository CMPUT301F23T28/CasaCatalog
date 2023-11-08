package com.cmput301f23t28.casacatalog.models;

import java.io.Serializable;

public class Tag implements Serializable {

    private final String name;
    private int uses;

    public Tag(String name) {
        this.name = name;
        this.uses = 0;
    }


    public String getName() {
        return this.name;
    }

    // This is a bit out of the scope of the user story
    // so it will be unused for now. QoL feature.
    public int getUses(){
        return this.uses;
    }

    public void setUses(int uses){
        this.uses = uses;
    }
}

package com.cmput301f23t28.casacatalog.models;

import java.io.Serializable;

/**
 * Represents a tag that can be associated with an item. This class implements Serializable,
 * allowing tag objects to be serialized for easy storage and transmission.
 */
public class Tag implements Serializable {

    private final String name;
    private int uses;

    /**
     * Constructs a Tag with the specified name and initializes its use count to 1.
     *
     * @param name The name of the tag.
     */
    public Tag(String name) {
        this.name = name;
        this.uses = 1;
    }

    /**
     * Gets the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the number of uses of the tag.
     * Note: This method is currently not in use and is intended as a quality of life feature for future implementation.
     *
     * @return The number of times the tag has been used.
     */
    public int getUses() {
        return this.uses;
    }

    /**
     * Sets the number of uses of the tag.
     * Note: This feature is currently out of scope and is intended for future quality of life enhancements.
     *
     * @param uses The number of uses to set for the tag.
     */
    public void setUses(int uses) {
        this.uses = uses;
    }
}

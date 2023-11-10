package com.cmput301f23t28.casacatalog;

import org.junit.Test;
import static org.junit.Assert.*;

import com.cmput301f23t28.casacatalog.models.Tag;

public class TagTest {

    @Test
    public void testTagConstructorAndGetName() {
        String name = "Electronics";
        Tag tag = new Tag(name);
        assertEquals("The name should match the constructor input.", name, tag.getName());
    }

    @Test
    public void testInitialUseCount() {
        Tag tag = new Tag("Test");
        assertEquals("Initial use count should be 1.", 1, tag.getUses());
    }

    @Test
    public void testSetAndGetUses() {
        Tag tag = new Tag("Test");
        int uses = 5;
        tag.setUses(uses);
        assertEquals("The number of uses should match the set value.", uses, tag.getUses());
    }
}

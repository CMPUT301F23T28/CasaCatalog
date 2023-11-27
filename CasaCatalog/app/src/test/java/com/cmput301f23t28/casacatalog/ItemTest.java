package com.cmput301f23t28.casacatalog;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        item = new Item();
    }

    @Test
    public void testSetAndGetId() {
        String id = "123";
        item.setId(id);
        assertEquals(id, item.getId());
    }

    @Test
    public void testSetAndGetName() {
        String name = "Test Item";
        item.setName(name);
        assertEquals(name, item.getName());
    }

    @Test
    public void testSetAndGetDate() {
        Date now = new Date();
        item.setDate(now);
        assertEquals(now, item.getDate());
    }

    @Test
    public void testSetAndGetDateFormatted() {
        String dateFormatted = "2023-11-10";
        item.setDateFormatted(dateFormatted);
        assertEquals(dateFormatted, item.getFormattedDate());
    }

    @Test
    public void testSetAndGetPrice() {
        Double price = 19.99;
        item.setPrice(price);
        assertEquals(price, item.getPrice());
    }

    @Test
    public void testSetAndGetPhoto() {
        ByteBuffer photo = ByteBuffer.allocate(10);
        item.setPhoto(photo);
        assertEquals(photo, item.getPhoto());
    }

    @Test
    public void testSetAndGetTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Electronics"));
        tags.add(new Tag("Furniture"));
        item.setTags(tags);
        assertEquals(tags, item.getTags());
    }

    @Test
    public void testAddAndRemoveTag() {
        Tag tag = new Tag("Electronics");
        item.addTag(tag);
        assertTrue(item.getTags().contains(tag));
        item.removeTag(tag);
        assertFalse(item.getTags().contains(tag));
    }

    @Test
    public void testGetTagsAsStrings() {
        item.addTag(new Tag("Tag1"));
        item.addTag(new Tag("Tag2"));
        ArrayList<String> tagStrings = new ArrayList<>(item.getTagsAsStrings());
        assertTrue(tagStrings.contains("Tag1"));
        assertTrue(tagStrings.contains("Tag2"));
    }

    @Test
    public void testSetAndGetMake() {
        String make = "Test Make";
        item.setMake(make);
        assertEquals(make, item.getMake());
    }

    @Test
    public void testSetAndGetModel() {
        String model = "Test Model";
        item.setModel(model);
        assertEquals(model, item.getModel());
    }

    @Test
    public void testSetAndGetDescription() {
        String description = "A test description";
        item.setDescription(description);
        assertEquals(description, item.getDescription());
    }

    @Test
    public void testSetAndGetComment() {
        String comment = "Test Comment";
        item.setComment(comment);
        assertEquals(comment, item.getComment());
    }

    @Test
    public void testSetAndGetSerialNumber() {
        String serialNumber = "SN123456";
        item.setSerialNumber(serialNumber);
        assertEquals(serialNumber, item.getSerialNumber());
    }

    @Test
    public void testSetAndGetSelected() {
        item.setSelected(true);
        assertTrue(item.getSelected());
        item.setSelected(false);
        assertFalse(item.getSelected());
    }
}

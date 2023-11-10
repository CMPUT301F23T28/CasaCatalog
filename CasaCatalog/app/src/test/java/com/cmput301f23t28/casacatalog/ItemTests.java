package com.cmput301f23t28.casacatalog;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.cmput301f23t28.casacatalog.models.Tag;

import org.junit.Test;

import java.util.ArrayList;

public class ItemTests {

    private Item item = new Item();

    @Test
    void setNameTest() {
        item.setName("Bike");
        assertEquals(item.getName(), "Bike");
    }

    @Test
    void setPriceTest() {
        double price = 499.99;
        item.setPrice(price);
        assertEquals(item.getPrice(), 499.99); //???
    }

    @Test
    void setDateTest() {
        item.setDateFormatted("12-12-2023");
        assertEquals(item.getDateFormatted(), "12-12-2023");
    }

    @Test
    void setMakeTest() {
        item.setMake("GT");
        assertEquals(item.getMake(), "GT");
    }

    @Test
    void setModelTest() {
        item.setMake("Transeo");
        assertEquals(item.getModel(), "Transeo");
    }

    @Test
    void setSerialNumberTest() {
        item.setSerialNumber("123456789");
        assertEquals(item.getSerialNumber(), "123456789");
    }

    @Test
    void setCommentTest() {
        item.setComment("Wow this bike will not get stolen");
        assertEquals(item.getComment(), "Wow this bike will not get stolen");
    }

    @Test
    void setTagsTest() {
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Metal"));
        tagList.add(new Tag("Expensive"));
        tagList.add(new Tag("Cool"));
        item.setTags(tagList);
        assertEquals(item.getTags(), tagList);
    }

    @Test
    void setDescriptionTest() {
        item.setDescription("What a cool metal bike");
        assertEquals(item.getDescription(), "What a cool metal bike");
    }
    

}

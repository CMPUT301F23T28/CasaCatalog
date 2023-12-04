package com.cmput301f23t28.casacatalog.helpers;

import com.cmput301f23t28.casacatalog.models.Filter;
import com.cmput301f23t28.casacatalog.models.Item;

import junit.framework.TestCase;

import java.util.function.Predicate;

/**
 * Tests for the Filter class
 */
public class FilterTest extends TestCase {

    /**
     * Tests the getCurrentType method
     */
    public void testGetCurrentType() {
        Filter filter = new Filter("date", "", "hello", "you");
        assertEquals(Filter.Type.date, filter.getCurrentType());
    }

    /**
     * Tests the setCurrentType method
     */
    public void testSetCurrentType() {
        String type = "description";
        Filter filter = new Filter("date", "", "hello", "you");
        filter.setCurrentType(type);
        assertEquals(filter.getCurrentType(), Filter.Type.description);
    }

    /**
     * Tests the getCurrentFilterType method
     */
    public void testGetCurrentFilterType() {
        Filter filter = new Filter("date", "equals", "hello", "you");
        assertEquals(Filter.FilterType.equals, filter.getCurrentFilterType());
    }

    /**
     * Tests the setCurrentFilterType method
     */
    public void testSetCurrentFilterType() {
        Filter filter = new Filter("date", "equals", "hello", "you");
        filter.setCurrentFilterType("notequals");
        assertEquals(filter.getCurrentFilterType(), Filter.FilterType.notequals);
    }

    /**
     * Tests the getVal1 method
     */
    public void testGetVal1() {
        String val1 = "burger";
        Filter filter = new Filter("date", "", val1, "you");
        assertEquals(val1, filter.getVal1());
    }

    /**
     * Tests the setVal1 method
     */
    public void testSetVal1() {
        String val1 = "burger";
        String newVal1 = "cheeseburger";
        Filter filter = new Filter("date", "", val1, "you");
        filter.setVal1(newVal1);
        assertEquals(newVal1, filter.getVal1());
    }

    /**
     * Tests the getGet2 method
     */
    public void testGetVal2() {
        String val2 = "burger";
        Filter filter = new Filter("date", "", "val1", val2);
        assertEquals(val2, filter.getVal2());
    }

    /**
     * Tests the setVal2 method
     */
    public void testSetVal2() {
        String val2 = "burger";
        String newVal2 = "cheeseburger";
        Filter filter = new Filter("date", "", "val1", val2);
        filter.setVal2(newVal2);
        assertEquals(newVal2, filter.getVal2());
    }

    /**
     * Tests the getFilterPredicate method
     */
    public void testGetFilterPredicate() {
        // set up an item
        Item item = new Item();
        item.setDescription("this sentence contains hello");

        String searchFor = "hello";
        Filter filter = new Filter("description", "contains", searchFor, "val2");

        Predicate<Item> predicate = filter.getFilterPredicate();

        assertTrue(predicate.test(item));
    }
}
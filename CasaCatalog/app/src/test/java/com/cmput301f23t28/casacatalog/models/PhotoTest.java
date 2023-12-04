package com.cmput301f23t28.casacatalog.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PhotoTest {
    @Test
    public void testConstructor() {
        String url = "https://google.ca";
        Photo photo = new Photo(url);
        assertEquals(url, photo.getUrl());
    }
    @Test
    public void testGetAndSetSelected() {
        Photo photo = new Photo("test");
        assertEquals(false, photo.getSelected());
        photo.setSelected(true);
        assertEquals(true, photo.getSelected());
    }
    @Test
    public void testToggleSelected() {
        Photo photo = new Photo("test");
        assertEquals(false, photo.getSelected());
        photo.toggleSelected();
        assertEquals(true, photo.getSelected());
    }
    @Test
    public void testGetURL() {
        Photo photo = new Photo("test");
        String url = photo.getUrl();
        assertEquals("test", url);
    }
}

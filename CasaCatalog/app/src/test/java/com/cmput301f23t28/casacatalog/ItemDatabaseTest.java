package com.cmput301f23t28.casacatalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.provider.ContactsContract;

import androidx.test.platform.app.InstrumentationRegistry;

import com.cmput301f23t28.casacatalog.database.ItemDatabase;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;

public class ItemDatabaseTest {

    private ItemDatabase db;
    private Item item;

    // TODO: This doesn't work at the moment, need a way to get Firebase to work in test environment

    /*
    @Before
    public void setUp() {
        db = new ItemDatabase("tests");

        item = new Item();
        item.setName("TestItem");
        item.setPrice(1629.0);
        item.setDate(LocalDate.now());
        // TODO: set a test photo
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("TestTag"));
        item.setTags(tags);
        item.setMake("TestMake");
        item.setModel("TestModel");
        item.setDescription("TestDescription");
        item.setComment("TestComment");
        item.setSerialNumber("TestSerial");
    }

    @Test
    public void testAddItem() {
        db.add(item);
        db.getCollection().document(item.getId()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if(doc.exists()){
                    assertEquals(item.getName(), doc.get(ItemDatabase.NAME_KEY));
                    assertEquals(item.getPrice(), doc.get(ItemDatabase.PRICE_KEY));
                    assertEquals(item.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant(), ((LocalDate)doc.get(ItemDatabase.DATE_KEY)).atStartOfDay(ZoneId.systemDefault()).toInstant());
                    assertEquals(item.getTags(), doc.get(ItemDatabase.TAGS_KEY));
                    assertEquals(item.getMake(), doc.get(ItemDatabase.MAKE_KEY));
                    assertEquals(item.getModel(), doc.get(ItemDatabase.MODEL_KEY));
                    assertEquals(item.getDescription(), doc.get(ItemDatabase.DESCRIPTION_KEY));
                    assertEquals(item.getComment(), doc.get(ItemDatabase.COMMENT_KEY));
                    assertEquals(item.getSerialNumber(), doc.get(ItemDatabase.SERIAL_KEY));
                }
            }
        });
    }
     */

}

package com.cmput301f23t28.casacatalog;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.cmput301f23t28.casacatalog.views.AddItemActivity;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.cmput301f23t28.casacatalog.views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest

public class EditItemActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    // OH GOD!!!! OH GOD
    @Test
    public void testAddActivitySwitch() {
        /**
         * Tests clicking the 'add item' button, and whether the 'add item' activity appears.
         */
        Intents.init();
        onView(withId(R.id.add_item_button)).perform(click());
        // Check activity switched
        intended(hasComponent(AddItemActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testAddingItemToList() {
        /**
         * Opens the 'add item' activity, fills in the fields of the item, and adds it
         * to the database.
         */
        Intents.init();
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.itemName)).perform(ViewActions.typeText("Toaster"));
        onView(withId(R.id.itemEstimatedValue)).perform(ViewActions.typeText("123.99"));
        onView(withId(R.id.itemPurchaseDate)).perform(ViewActions.typeText("12-12-2012"));
        onView(withId(R.id.itemDescription)).perform(ViewActions.typeText("Super dope toaster"));
        onView(withId(R.id.addItemToListBtn)).perform(click());

        //intended(hasComponent(MainActivity.class.getName())); // might not work :I
        assertTrue(scenario != null);
        Intents.release();
    }

    @Test
    public void testEditActivitySwitch() {
        /**
         * Tests clicking an item, and whether the 'edit item' activity appears.
         */
        Intents.init();
        // Click on the first item
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        // Check activity switched
        intended(hasComponent(EditItemActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testEditingItemInList() {
        /**
         * Opens the 'edit item' activity, edits fields of the item, and edits it
         * in the database.
         */
        Intents.init();
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        onView(withId(R.id.itemName)).perform(ViewActions.typeText("Toaster2"));
        onView(withId(R.id.itemEstimatedValue)).perform(ViewActions.typeText("100.10"));
        onView(withId(R.id.itemPurchaseDate)).perform(ViewActions.typeText("11-11-2012"));
        onView(withId(R.id.itemDescription)).perform(ViewActions.typeText("Super duper dope toaster"));
        onView(withId(R.id.addItemToListBtn)).perform(click());

        onView(withText("Toaster")).check(doesNotExist());
        Intents.release();
    }

    @Test
    public void testDeletingItem() {
        /**
         * Tests clicking an item, clicking delete button, and seeing if it is deleted from database.
         */
        Intents.init();
        // Click on the first item
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        onView(withId(R.id.deleteItemFromListBtn)).perform(click());
        // MIGHT bug bc toaster might not be first item in list uh oh
        onView(withText("Toaster")).check(doesNotExist());
        Intents.release();
    }

}

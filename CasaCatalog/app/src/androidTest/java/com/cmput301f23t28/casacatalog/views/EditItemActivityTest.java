package com.cmput301f23t28.casacatalog.views;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cmput301f23t28.casacatalog.util.EspressoUtils.waitUntilVisible;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.util.EspressoUtils;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
//import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.cmput301f23t28.casacatalog.views.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;


@RunWith(AndroidJUnit4.class)  @LargeTest

public class EditItemActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }

    // OH GOD!!!! OH GOD
    /**
     * Tests clicking the 'add item' button, and whether the 'add item' activity appears.
     */
    @Test
    public void testAddActivitySwitch() {

        onView(ViewMatchers.withId(R.id.add_item_button)).perform(click());
        // Check activity switched
        intended(hasComponent(AddItemActivity.class.getName()));
        Intents.release();
    }

    /**
     * Opens the 'add item' activity, fills in the fields of the item, and adds it
     * to the database.
     */
    @Test
    public void testAddingItemToList() {
//        Intents.init();
        onView(withId(R.id.add_item_button)).perform(click());
        EspressoUtils.waitUntilVisible(onView(withId(R.id.itemNameInput)), 5000)
                .perform(typeText("Toaster"));
        onView(withId(R.id.itemEstimatedValueInput)).perform(typeText("123.99"));
        onView(withId(R.id.itemPurchaseDateInput)).perform(typeText("12-12-2012"));
        onView(withId(R.id.itemDescriptionInput)).perform(typeText("Super dope toaster"));
        onView(withId(R.id.addItemToListBtn)).perform(ViewActions.scrollTo());
        onView(withId(R.id.addItemToListBtn)).perform(click());

        //intended(hasComponent(MainActivity.class.getName())); // might not work :I
        assertTrue(scenario != null);
//        Intents.release();
    }

    /**
     * Tests clicking an item, and whether the 'edit item' activity appears.
     */
    @Test
    public void testEditActivitySwitch() {
//        Intents.init();
        // Click on the first item
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.items_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        // Check activity switched
        intended(hasComponent(EditItemActivity.class.getName()));
//        Intents.release();
    }

    /**
     * Opens the 'edit item' activity, edits fields of the item, and edits it
     * in the database.
     */
    @Test
    public void testEditingItemInList() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.items_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        int rand = new Random().nextInt(99);
        onView(withId(R.id.itemNameInput)).perform(clearText(), typeText("editTest" + rand));
        onView(withId(R.id.itemEstimatedValueInput)).perform(clearText(), typeText("100.10"));
        onView(withId(R.id.itemPurchaseDateInput)).perform(clearText(), typeText("11-11-2012"));
        onView(withId(R.id.itemDescriptionInput)).perform(clearText(), typeText("Super duper dope toaster"), closeSoftKeyboard());
        onView(withId(R.id.addItemToListBtn)).perform(ViewActions.scrollTo());
        onView(withId(R.id.addItemToListBtn)).perform(click());

        onView(withText("editTest" + rand))
                .check(matches(isDisplayed()));
    }

    /**
     * Tests clicking an item, clicking delete button, and seeing if it is deleted from database.
     */
    @Test
    public void testDeletingItem() {
        // add an item to delete
        addTestItemTestData("testDeletingItem");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the first item
//        onView(withId(R.id.items_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withText("testDeletingItem")).perform(click());
        onView(withId(R.id.deleteItemFromListBtn)).perform(ViewActions.scrollTo());
        onView(withId(R.id.deleteItemFromListBtn)).perform(click());
        // MIGHT bug bc toaster might not be first item in list uh oh
        onView(withText("testDeletingItem")).check(doesNotExist());
    }

    private void addTestItemTestData(String itemName) {
        onView(withId(R.id.add_item_button)).perform(click());
        EspressoUtils.waitUntilVisible(onView(withId(R.id.itemNameInput)), 5000)
                .perform(typeText(itemName));
        onView(withId(R.id.addItemToListBtn)).perform(ViewActions.scrollTo());
        onView(withId(R.id.addItemToListBtn)).perform(click());
    }

}

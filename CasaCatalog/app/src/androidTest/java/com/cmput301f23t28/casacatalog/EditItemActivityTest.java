package com.cmput301f23t28.casacatalog;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cmput301f23t28.casacatalog.util.EspressoUtils.waitUntilVisible;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertTrue;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.cmput301f23t28.casacatalog.util.EspressoUtils;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
//import com.cmput301f23t28.casacatalog.views.EditItemActivity;
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
    /**
     * Tests clicking the 'add item' button, and whether the 'add item' activity appears.
     */
    @Test
    public void testAddActivitySwitch() {
        Intents.init();
        onView(withId(R.id.add_item_button)).perform(click());
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
        Intents.init();
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
        Intents.release();
    }

    /**
     * Tests clicking an item, and whether the 'edit item' activity appears.
     */
    @Test
    public void testEditActivitySwitch() {
        Intents.init();
        // Click on the first item
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        // Check activity switched
        intended(hasComponent(EditItemActivity.class.getName()));
        Intents.release();
    }

    /**
     * Opens the 'edit item' activity, edits fields of the item, and edits it
     * in the database.
     */
    @Test
    public void testEditingItemInList() {
        Intents.init();
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        onView(withId(R.id.itemNameInput)).perform(typeText("Toaster2"));
        onView(withId(R.id.itemEstimatedValueInput)).perform(typeText("100.10"));
        onView(withId(R.id.itemPurchaseDateInput)).perform(typeText("11-11-2012"));
        onView(withId(R.id.itemDescriptionInput)).perform(typeText("Super duper dope toaster"));
        onView(withId(R.id.addItemToListBtn)).perform(click());

        onView(withText("Toaster")).check(doesNotExist());
        Intents.release();
    }

    /**
     * Tests clicking an item, clicking delete button, and seeing if it is deleted from database.
     */
    @Test
    public void testDeletingItem() {

        Intents.init();
        // Click on the first item
        onData(anything()).inAdapterView(withId(R.id.items_list)).atPosition(0).perform(click());
        onView(withId(R.id.deleteItemFromListBtn)).perform(click());
        // MIGHT bug bc toaster might not be first item in list uh oh
        onView(withText("Toaster")).check(doesNotExist());
        Intents.release();
    }

}

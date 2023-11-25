package com.cmput301f23t28.casacatalog.views;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.util.EspressoUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

/**
 * Tests for the MainActivity activity.
 */
@RunWith(AndroidJUnit4.class) @LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    /**
     * Test for deleting single item.
     */
    @Test
    public void testDeleteSingleItem() {
        int rand = new Random().nextInt(99);
        String name = "testDeletingItem" + rand;
        addTestItemTestData(name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText(name)).perform(ViewActions.scrollTo(), longClick());
        onView(withId(R.id.delete_items_button)).perform(click());
        onView(withText(name)).check(doesNotExist());
    }

    /**
     * Test for deleting multiple items.
     */
    @Test
    public void testDeleteMultiItem() {
        int rand = new Random().nextInt(99);
        String name = "testDeletingItem" + rand;
        addTestItemTestData(name);

        rand = new Random().nextInt(99);
        String name2 = "testDeletingItem" + rand;
        addTestItemTestData(name2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText(name)).perform(ViewActions.scrollTo(), longClick());
        onView(withText(name2)).perform(ViewActions.scrollTo(), click());
        onView(withId(R.id.delete_items_button)).perform(click());
        onView(withText(name)).check(doesNotExist());
        onView(withText(name2)).check(doesNotExist());
    }

    /**
     * TODO: Refactor so the code is not duplicated in other tests.
     * Adds an item to the database.
     * @param itemName
     */
    public void addTestItemTestData(String itemName) {
        onView(withId(R.id.add_item_button)).perform(click());
        EspressoUtils.waitUntilVisible(onView(withId(R.id.itemNameInput)), 5000)
                .perform(typeText(itemName));
        onView(withId(R.id.addItemToListBtn)).perform(ViewActions.scrollTo());
        onView(withId(R.id.addItemToListBtn)).perform(click());
    }
}

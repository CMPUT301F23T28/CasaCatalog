package com.cmput301f23t28.casacatalog.util;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.Is.is;

import android.view.View;
import android.widget.Checkable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import junit.framework.AssertionFailedError;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EspressoUtils {
    public static ViewInteraction waitUntilVisible(ViewInteraction viewInteraction, long maxMillis) {
        // Perform a loop to check for the view's visibility
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < maxMillis) {
            try {
                viewInteraction.check(matches(ViewMatchers.isDisplayed()));
                return viewInteraction;
            } catch (NoMatchingViewException | AssertionFailedError ignored) {
                // View is not visible yet, continue checking
            }
        }
        throw new AssertionFailedError("View not visible within timeout");
    }

    /**
     * https://stackoverflow.com/questions/37819278/android-espresso-click-checkbox-if-not-checked
     * @param checked condition of a checkbox
     */
    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {}

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };


    }
    /** This Matcher helps us to perform actions on Views in RecyclerViews that we don't have another way of accessing.
     *  It was originally needed to perform click operations on checkboxes in a RecyclerView.
     * https://stackoverflow.com/a/43552179/255217
     */
    public static Matcher<View> getElementFromMatchAtPosition(final Matcher<View> matcher, final int position) {
        return new BaseMatcher<View>() {
            int counter = 0;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == position) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at hierarchy position "+position);
            }
        };
    }
}

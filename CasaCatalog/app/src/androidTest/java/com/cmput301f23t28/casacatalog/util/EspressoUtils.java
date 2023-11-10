package com.cmput301f23t28.casacatalog.util;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import junit.framework.AssertionFailedError;

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
}

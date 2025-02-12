package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DeliveryTests {

    @Rule
    public ActivityScenarioRule<DeliveryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(DeliveryActivity.class);

    @Before
    public void setup() {
        // Ensure activity is launched
    }

    @Test
    public void testLayoutElementsExist() {
        // Check if key UI elements exist
        onView(withId(R.id.items)).check(matches(isDisplayed()));
        onView(withId(R.id.BarcodeBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.completeBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testBarcodeButtonClick() {
        // Click on Barcode Button
        onView(withId(R.id.BarcodeBtn)).perform(click());

        // Ensure a Toast is shown
        onView(withText("Function not available right now.")).check(matches(isDisplayed()));
    }

    @Test
    public void testCompleteOrderButtonClick() {
        // Click on Complete Order Button
        onView(withId(R.id.completeBtn)).perform(click());

        // Check if the confirmation Toast is shown
        onView(withText("Sending Confirmation email. Logging out...")).check(matches(isDisplayed()));
    }

    @Test
    public void testRandomItemGeneration() {
        // Check if at least one item is displayed in the ListView
        onView(withId(R.id.items)).check(matches(isDisplayed()));

        // Scroll to an item in the ListView
        onView(withId(R.id.items)).perform(scrollTo());
    }
}
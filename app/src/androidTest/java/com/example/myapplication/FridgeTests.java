package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FridgeTests {

    @Rule
    public ActivityScenarioRule<InventoryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(InventoryActivity.class);

    @Test
    public void testLayoutElementsExist() {
        // Check if key UI elements exist
        onView(withId(R.id.tableLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.addRowButton)).check(matches(isDisplayed()));
        onView(withId(R.id.createReportButton)).check(matches(isDisplayed()));
        onView(withId(R.id.searchIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.searchContainer)).check(matches(isDisplayed()));
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
        onView(withId(R.id.hamburger_icon)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddRowFunctionality() {
        // Click on "Add Row" button
        onView(withId(R.id.addRowButton)).perform(click());

        // Check if the dialog is displayed
        onView(withId(R.id.itemNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.quantityEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.expiryDateEditText)).check(matches(isDisplayed()));

        // Click on Save Button
        onView(withId(R.id.saveButton)).perform(click());
    }

    @Test
    public void testSearchFunctionality() {
        // Click search icon
        onView(withId(R.id.searchIcon)).perform(click());

        // Ensure search container is visible
        onView(withId(R.id.searchContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void testReportButtonClick() {
        // Click on Create Report button
        onView(withId(R.id.createReportButton)).perform(click());

        // Ensure dialog appears (mock UI behavior for confirmation)
        onView(withText("Choose Report Type")).check(matches(isDisplayed()));
    }
}
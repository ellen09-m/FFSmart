package com.example.myapplication;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.rule.ActivityScenarioRule;
import androidx.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import com.example.myapplication.MainActivity;

public class AuthenticationTests {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEmptyLogin() {
        onView(withId(R.id.input_email)).perform(typeText(""), closeSoftKeyboard());;
    }

    @Test

    public void testImproperLogin() {
        onView(withId(R.id.input_id)).perform(typeText("ayo.com"), closeSoftKeyboard());;
    }

    @Test
    public void testProperLogin() {
        // Enter valid credentials
        onView(withId(R.id.input_id)).perform(typeText("ayo@outlook.com"), closeSoftKeyboard());

        // Click login
        onView(withId(R.id.btn_sign_in)).perform(click());

        // Check if redirected to home screen
        //onView(withId(R.id.toolbarChefPage)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpButton(){
        onView(withId(R.id.btn_sign_in)).perform(click());
    }
}
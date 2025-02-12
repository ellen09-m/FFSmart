package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class MaintenanceTests {

    @Rule
    public ActivityTestRule<MaintenanceActivity> activityRule =
            new ActivityTestRule<>(MaintenanceActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    /**
     * Test if the FAQ RecyclerView is displayed
     */
    @Test
    public void testFAQRecyclerViewIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.faqRecyclerView))
                .check(matches(isDisplayed()));
    }

    /**
     * Test if clicking the "Add Maintenance Issue" button opens LogMaintenanceActivity
     */
    @Test
    public void testAddMaintenanceIssueButtonOpensActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.addMaintenanceIssueButton))
                .perform(click());
        intended(hasComponent(LogMaintenanceActivity.class.getName()));
    }

    /**
     * Test if clicking "Contact Support" button shows the support dialog
     */
    @Test
    public void testContactSupportDialogAppears() {
        Espresso.onView(ViewMatchers.withId(R.id.contactSupportButton))
                .perform(click());

        Espresso.onView(ViewMatchers.withText("Support"))
                .check(matches(isDisplayed()));  // Verify dialog appears
    }

    /**
     * Test if clicking phone number in the support dialog opens dialer
     */
    @Test
    public void testPhoneNumberClickOpensDialer() {
        Espresso.onView(ViewMatchers.withId(R.id.contactSupportButton))
                .perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.phoneNumberText))
                .perform(click());

        intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));
    }

    /**
     * Test if the email button works in the support dialog
     */
    @Test
    public void testSendEmailButtonOpensEmailApp() {
        Espresso.onView(ViewMatchers.withId(R.id.contactSupportButton))
                .perform(click());

        // Fill out email form
        Espresso.onView(ViewMatchers.withId(R.id.emailName))
                .perform(ViewActions.typeText("John Doe"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailSubject))
                .perform(ViewActions.typeText("Test Subject"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.emailMessage))
                .perform(ViewActions.typeText("Test Message"), ViewActions.closeSoftKeyboard());

        // Click send button
        Espresso.onView(ViewMatchers.withId(R.id.sendEmailButton))
                .perform(click());

        intended(IntentMatchers.hasAction(Intent.ACTION_SENDTO));
    }

    /**
     * Test if clicking the hamburger menu opens the menu
     */
    @Test
    public void testHamburgerMenuOpens() {
        Espresso.onView(ViewMatchers.withId(R.id.hamburger_icon))
                .perform(click());

        Espresso.onView(ViewMatchers.withText("Dashboard"))  // Assuming menu contains "Dashboard"
                .check(matches(isDisplayed()));
    }

    /**
     * Test if selecting "Dashboard" from the menu opens DashboardActivity
     */
    @Test
    public void testMenuNavigationToDashboard() {
        Espresso.onView(ViewMatchers.withId(R.id.hamburger_icon))
                .perform(click());

        Espresso.onView(ViewMatchers.withText("Dashboard"))
                .perform(click());

        intended(hasComponent(DashboardActivity.class.getName()));
    }
}
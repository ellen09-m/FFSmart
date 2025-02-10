package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public static class BankDetailsActivityTest {

        private BankDetailsActivity bankDetailsActivity;

        @Before
        public void setUp() {
            // Create a new instance of BankDetailsActivity for each test
            bankDetailsActivity = new BankDetailsActivity();
        }

        @Test
        public void testIsValidEmail_validEmail() {
            // Test valid email addresses
            assertTrue(bankDetailsActivity.isValidEmail("test@example.com"));
            assertTrue(bankDetailsActivity.isValidEmail("hello.world@domain.co"));
        }

        @Test
        public void testIsValidEmail_invalidEmail() {
            // Test invalid email addresses
            assertFalse(bankDetailsActivity.isValidEmail("test.com"));
            assertFalse(bankDetailsActivity.isValidEmail("hello@world"));
            assertFalse(bankDetailsActivity.isValidEmail("test@.com"));
        }
    }
}
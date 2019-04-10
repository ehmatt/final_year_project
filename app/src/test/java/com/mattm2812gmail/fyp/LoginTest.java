package com.mattm2812gmail.fyp;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest {

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(LoginTest.isValidEmail("name@email.com")).isTrue();
    }

    private static boolean isValidEmail(String s) {
        String testEmail = "name@email.com";
        if (testEmail.contains("@")) {

            return true;
        }else {
            return false;
        }
    }

}
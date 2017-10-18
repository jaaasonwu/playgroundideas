package com.playgroundideas.playgroundideas;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.playgroundideas.playgroundideas.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.playgroundideas.playgroundideas.TestUtil.matchToolbarTitle;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private String mUsername;
    private String mValidPassword;
    private String mInvalidPassword;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void initCredentials() {
        mUsername = "platypus";
        mInvalidPassword = "111111";
        mValidPassword = "platypus";
    }

    @Test
    public void switchSignup() {
        onView(withId(R.id.switch_signup)).perform(click());
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.action_sign_up);
        matchToolbarTitle(title);

        onView(withId(R.id.switch_login)).perform(click());
        title = InstrumentationRegistry.getTargetContext().getString(R.string.action_log_in);
        matchToolbarTitle(title);
    }

    @Test
    public void failedLogin() {
        onView(withId(R.id.email)).perform(typeText(mUsername), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(mInvalidPassword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.action_log_in);
        matchToolbarTitle(title);
    }

    @Test
    public void successfulLogin() {
        onView(withId(R.id.email)).perform(typeText(mUsername), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(mValidPassword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.app_name);
        matchToolbarTitle(title);
    }
}

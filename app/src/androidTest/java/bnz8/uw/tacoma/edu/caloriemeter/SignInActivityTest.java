package bnz8.uw.tacoma.edu.caloriemeter;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Random;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * A test class that runs instrumented Espresso test on the MainActivity
 * It tests for a valid and invalid user login attempts using both user email and password
 * Created by birhanunega on 3/9/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInActivityTest {
    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test.
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /**
     * A method to test for long password entry
     * @throws InterruptedException
     */
    @Test
    public void testInvalidPasswordLong() throws InterruptedException {
        Thread.sleep(3000);
        // Type text and then press the button.
        onView(withId(R.id.userid_edit))
                .perform(typeText("test@yahoo.com"));
        onView(withId(R.id.pwd_edit))
                .perform(typeText("testing1234"));
        onView(withId(R.id.login_button_nonfacebook))
                .perform(click());

        onView(withText("Failed! Incorrect password."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    /**
     * A method to test log in with invalid email
     * @throws InterruptedException
     */
    @Test
    public void testInvalidEmail() throws InterruptedException {

        Thread.sleep(3000);
        // Type text and then press the button.
        onView(withId(R.id.userid_edit))
                .perform(typeText("testyahoo.com"));
        onView(withId(R.id.pwd_edit))
                .perform(typeText("testing"));
        onView(withId(R.id.login_button_nonfacebook))
                .perform(click());

        onView(withText("Failed! Please enter a valid email."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * A method to test log in with invalid password
     * @throws InterruptedException
     */
    @Test
    public void testInvalidPassword() throws InterruptedException {
        Thread.sleep(3000);
        // Type text and then press the button.
        onView(withId(R.id.userid_edit))
                .perform(typeText("test@yahoo.com"));
        onView(withId(R.id.pwd_edit))
                .perform(typeText("tes"));
        onView(withId(R.id.login_button_nonfacebook))
                .perform(click());

        onView(withText("Failed! Please enter a valid password (longer than five characters)."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    /**
     * A method to test registering with invalid email
     * @throws InterruptedException
     */
    @Test
    public void testSignUpInvalidEmail() throws InterruptedException {
        Thread.sleep(3000);
        onView(ViewMatchers.withId(R.id.action_logout))
                .perform(click());
        onView(ViewMatchers.withId(R.id.signUp_Button_toFragment)).perform(click());

        // Type text and then press the button.
        onView(withId(R.id.email_signUp))
                .perform(typeText("test"));
        onView(withId(R.id.create_pw))
                .perform(typeText("123456"));
        onView(withId(R.id.sign_up_buton))
                .perform(click());
    }
    /**
     * A method to test registering with invalid password
     * @throws InterruptedException
     */
    @Test
    public void testSignUpInvalidPassword() throws InterruptedException {
        Thread.sleep(3000);
        onView(ViewMatchers.withId(R.id.signUp_Button_toFragment)).perform(click());
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(4) + 1)
                + (random.nextInt(9) + 1) + (random.nextInt(7) + 1)
                + (random.nextInt(4) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Type text and then press the button.
        onView(withId(R.id.email_signUp))
                .perform(typeText(email));
        onView(withId(R.id.create_pw))
                .perform(typeText("1"));
        onView(withId(R.id.sign_up_buton))
                .perform(click());

        onView(withText("Failed to sign up! Please enter a valid password (longer than five characters)."))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }
    /**
     * A method to test registering with a randomly generated valid email and password
     * @throws InterruptedException
     */
    @Test
    public void testSignUp() throws InterruptedException {
        Thread.sleep(3000);

        onView(ViewMatchers.withId(R.id.signUp_Button_toFragment)).perform(click());
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(4) + 1)
                + (random.nextInt(9) + 1) + (random.nextInt(7) + 1)
                + (random.nextInt(4) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Type text and then press the button.
        onView(withId(R.id.email_signUp))
                .perform(typeText(email));
        onView(withId(R.id.create_pw))
                .perform(typeText("123456"));
        onView(withId(R.id.sign_up_buton))
                .perform(click());

        onView(withText("User Successfully registered"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }
//
    /**
     * A method to test logging in with a randomly generated valid email and password
     * @throws InterruptedException
     */
    @Test
    public void testValidLogin() throws InterruptedException {
    Thread.sleep(1000);
    // Type text and then press the button.
    onView(withId(R.id.userid_edit))
            .perform(typeText("test@yahoo.com"));
    onView(withId(R.id.pwd_edit))
            .perform(typeText("testing"));
    onView(withId(R.id.login_button_nonfacebook))
            .perform(click());

    onView(withText("test@yahoo.com"))
            .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
}

}






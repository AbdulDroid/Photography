package com.example.android.photography;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Abdulkarim on 7/19/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new
            ActivityTestRule<>(MainActivity.class);

    @Test
    public void MainFragmentTest() {

        //This test verifies that the UI elements of the main fragment are displayed as expected
        onView(withId(R.id.main_text)).check(matches(isDisplayed()));
        onView(withId(R.id.take_course_button)).check(matches(isDisplayed()));
        onView(withId(R.id.assessment_button)).check(matches(isDisplayed()));
    }

    @Test
    public void QuizFragmentTest() {
        //This test verifies that the QuizFragment is displayed when the Assessment button is clicked

        //First click on the button
        onView(withId(R.id.assessment_button)).perform(click());

        //Assert if the QuizFragment is displayed

        QuizFragmentViewAssertions();

    }

    @Test
    public void TimeOutViewTest() {

        //This test verifies that the FeedbackFragment is displayed on Time out
        String timeout = "Time Up!!!";
        //First start the QuizFragment
        onView(withId(R.id.assessment_button)).perform(click());

        //Sleep for 1 minute
        try {
            Thread.sleep(60500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.status)).check(matches(isDisplayed()));
        onView(withId(R.id.status)).check(matches(withText(timeout)));
        onView(withId(R.id.status_comment)).check(matches(isDisplayed()));
        onView(withId(R.id.quit_test_button)).check(matches(isDisplayed()));
        onView(withId(R.id.next_question)).check(matches(isDisplayed()));
    }


    @Test
    public void FinalFeedbackTest() {

        onView(withId(R.id.assessment_button)).perform(click());
        QuizFragmentViewActions();
        onView(withId(R.id.status)).check(matches(isDisplayed()));
        onView(withId(R.id.status_comment)).check(matches(isDisplayed()));
        onView(withId(R.id.continue_button)).check(matches(isDisplayed()));
    }


    @Test
    public void FeedbackFragmentContinueButtonTest() {

        //This test verifies that button on the feedback fragment navigates back to the right fragment
        //Check the buttons on the timeout version of the feedback fragment
        //First navigate to the timeout version of the feedback fragment
        //This test verifies that the FeedbackFragment is displayed on Time out
        TimeOutViewTest();
        //check if the quiz fragment is displayed
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.next_question)).perform(click());
        QuizFragmentViewAssertions();
    }

    @Test
    public void FeedbackFragmentQuitBUttonTest() {
        //Check for the quit button on the timeout version of the feedback fragment
        TimeOutViewTest();
        //Click on the QUit button
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.quit_test_button)).perform(click());
        //Check if the main fragment is displayed
        MainFragmentTest();
    }

    public void QuizFragmentViewAssertions() {

        onView(withId(R.id.question_id_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.time_view)).check(matches(isDisplayed()));
        onView(withId(R.id.time_left)).check(matches(isDisplayed()));
        onView(withId(R.id.question_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.option_one_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.option_two_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.option_three_tv)).check(matches(isDisplayed()));

    }

    public void QuizFragmentViewActions() {
        onView(withId(R.id.option_two_tv)).perform(click());
        QuizFragmentViewAssertions();
        onView(withId(R.id.option_three_tv)).perform(click());
        QuizFragmentViewAssertions();
        onView(withId(R.id.option_one_tv)).perform(click());
        QuizFragmentViewAssertions();
        onView(withId(R.id.option_three_tv)).perform(click());
        QuizFragmentViewAssertions();
        onView(withId(R.id.option_one_tv)).perform(click());
    }
}

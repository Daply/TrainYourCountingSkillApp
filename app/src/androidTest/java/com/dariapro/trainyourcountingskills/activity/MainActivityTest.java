package com.dariapro.trainyourcountingskills.activity;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.entity.QuestionType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void isViewValid() {
        Log.i("@Test","Testing random questions start activity, random examples click");
        onView(withId(R.id.main_fragment)).check(matches(notNullValue()));
    }

    @Test
    public void clickOnRandomExamples() {
        Log.i("@Test","Testing main activity, random examples click");
        onView(withId(R.id.random_examples))
                .perform(click());
        Activity instance = getActivityInstance();
        intended(toPackage(getActivityInstance().getPackageName()));
        intended(hasExtra(getActivityInstance().getString(R.string.MODE), Mode.RANDOM.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.QUESTION_TYPE), QuestionType.QUESTION.name()));
        boolean b = (instance instanceof RandomQuestionStartActivity);
        assertTrue(b);
    }

    @Test
    public void clickOnRandomExpressions() {
        Log.i("@Test","Testing main activity, random expressions click");
        onView(withId(R.id.random_expressions))
                .perform(click());
        Activity instance = getActivityInstance();
        intended(toPackage(getActivityInstance().getPackageName()));
        intended(hasExtra(getActivityInstance().getString(R.string.MODE), Mode.RANDOM.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.QUESTION_TYPE), QuestionType.EXPRESSION.name()));
        boolean b = (instance instanceof RandomQuestionStartActivity);
        assertTrue(b);
    }

    @Test
    public void clickOnProblems() {
        Log.i("@Test","Testing main activity, problems click");
        onView(withId(R.id.solve_problems))
                .perform(click());
        Activity instance = getActivityInstance();
        intended(toPackage(getActivityInstance().getPackageName()));
        intended(hasExtra(getActivityInstance().getString(R.string.MODE), Mode.SIMPLE.name()));
        boolean b = (instance instanceof CategoryActivity);
        assertTrue(b);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }
}

package com.dariapro.trainyourcountingskills.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.pager.RandomQuestionPagerActivity;
import com.dariapro.trainyourcountingskills.entity.Mode;
import com.dariapro.trainyourcountingskills.entity.QuestionType;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RandomQuestionStartActivityTest {

    @Rule
    public IntentsTestRule<RandomQuestionStartActivity> intentsRule =
            new IntentsTestRule<>(RandomQuestionStartActivity.class, false, false);

    @Test
    public void startButtonForRandomQuestionsExtrasTest() {
        Log.i("@Test","Testing random questions start activity, start click");
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent();
        i.putExtra(appContext.getString(R.string.MODE), Mode.RANDOM.name());
        i.putExtra(appContext.getString(R.string.QUESTION_TYPE), QuestionType.QUESTION.name());
        intentsRule.launchActivity(i);
        onView(withId(R.id.level_spinner))
                .perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.level_spinner)).check(matches(withSpinnerText(containsString("Level 3"))));
        onView(withId(R.id.time_spinner))
                .perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.time_spinner)).check(matches(withSpinnerText(containsString("1 minute"))));
        onView(withId(R.id.minus))
                .perform(click());
        onView(withId(R.id.root))
                .perform(click());
        onView(withId(R.id.start_randoms))
                .perform(click());
        Activity instance = getActivityInstance();
        intended(toPackage(getActivityInstance().getPackageName()));
        intended(hasExtra(getActivityInstance().getString(R.string.MODE), Mode.RANDOM.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.QUESTION_TYPE), QuestionType.QUESTION.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.LEVEL_EXTRA), 3));
        intended(hasExtra(getActivityInstance().getString(R.string.TIME_EXTRA), 1));
        intended(hasExtra(getActivityInstance().getString(R.string.PLUS_EXTRA), false));
        intended(hasExtra(getActivityInstance().getString(R.string.MULTIPLY_EXTRA), false));
        intended(hasExtra(getActivityInstance().getString(R.string.MINUS_EXTRA), true));
        intended(hasExtra(getActivityInstance().getString(R.string.DIVIDE_EXTRA), false));
        intended(hasExtra(getActivityInstance().getString(R.string.ROOT_EXTRA), true));
        boolean b = (instance instanceof RandomQuestionPagerActivity);
        assertTrue(b);
    }

    @Test
    public void startButtonForRandomExpressionsExtrasTest() {
        Log.i("@Test","Testing random questions start activity, start click");
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent();
        i.putExtra(appContext.getString(R.string.MODE), Mode.RANDOM.name());
        i.putExtra(appContext.getString(R.string.QUESTION_TYPE), QuestionType.EXPRESSION.name());
        intentsRule.launchActivity(i);
        onView(withId(R.id.level_spinner))
                .perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.level_spinner)).check(matches(withSpinnerText(containsString("Level 3"))));
        onView(withId(R.id.time_spinner))
                .perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.time_spinner)).check(matches(withSpinnerText(containsString("1 minute"))));
        onView(withId(R.id.plus)).check(matches(not(isDisplayed())));
        onView(withId(R.id.minus)).check(matches(not(isDisplayed())));
        onView(withId(R.id.multiply)).check(matches(not(isDisplayed())));
        onView(withId(R.id.divide)).check(matches(not(isDisplayed())));
        onView(withId(R.id.root)).check(matches(not(isDisplayed())));
        onView(withId(R.id.start_randoms))
                .perform(click());
        Activity instance = getActivityInstance();
        intended(toPackage(getActivityInstance().getPackageName()));
        intended(hasExtra(getActivityInstance().getString(R.string.MODE), Mode.RANDOM.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.QUESTION_TYPE), QuestionType.EXPRESSION.name()));
        intended(hasExtra(getActivityInstance().getString(R.string.LEVEL_EXTRA), 3));
        intended(hasExtra(getActivityInstance().getString(R.string.TIME_EXTRA), 1));
        boolean b = (instance instanceof RandomQuestionPagerActivity);
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

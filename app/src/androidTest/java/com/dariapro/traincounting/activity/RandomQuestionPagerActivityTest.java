package com.dariapro.traincounting.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.entity.Mode;
import com.dariapro.traincounting.entity.QuestionType;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RandomQuestionPagerActivityTest {

    @Rule
    public IntentsTestRule<RandomQuestionPagerActivity> intentsRule =
            new IntentsTestRule<>(RandomQuestionPagerActivity.class, false, false);

    @Test
    public void startButtonForRandomQuestionsExtrasTest() {
        Log.i("@Test","Testing random questions start activity, start click");
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent();
        i.putExtra(appContext.getString(R.string.MODE), Mode.RANDOM.name());
        i.putExtra(appContext.getString(R.string.QUESTION_TYPE), QuestionType.QUESTION.name());
        i.putExtra(appContext.getString(R.string.LEVEL_EXTRA), 2);
        i.putExtra(appContext.getString(R.string.TIME_EXTRA), 5);
        i.putExtra(appContext.getString(R.string.PLUS_EXTRA), false);
        i.putExtra(appContext.getString(R.string.MINUS_EXTRA), true);
        i.putExtra(appContext.getString(R.string.MULTIPLY_EXTRA), false);
        i.putExtra(appContext.getString(R.string.DIVIDE_EXTRA), true);
        i.putExtra(appContext.getString(R.string.ROOT_EXTRA), true);
        intentsRule.launchActivity(i);
        onView(withId(R.id.question_expression))
                .check(matches(matchesQuestion("\\s*[√]*[0-9]+\\s*[-/]\\s*[√]*[0-9]+\\s*")));
        onView(withId(R.id.timer))
                .check(matches(matchesQuestion("[0][0-5]\\:[0-9][0-9]")));
        onView(withId(R.id.one_button))
                .perform(click());
        onView(withId(R.id.two_button))
                .perform(click());
        onView(withId(R.id.three_button))
                .perform(click());
        onView(withId(R.id.four_button))
                .perform(click());
        onView(withId(R.id.five_button))
                .perform(click());
        onView(withId(R.id.six_button))
                .perform(click());
        onView(withId(R.id.seven_button))
                .perform(click());
        onView(withId(R.id.eight_button))
                .perform(click());
        onView(withId(R.id.nine_button))
                .perform(click());
        onView(withId(R.id.question_answer))
                .check(matches(withText("123456789")));
        onView(withId(R.id.clear_all_button))
                .perform(click());
        onView(withId(R.id.question_answer))
                .check(matches(withText("")));
        intentsRule.finishActivity();
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

    public Matcher<View> matchesQuestion(final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Matches TextView the value:  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (view != null) {
                    if (view instanceof TextView) {
                        String text = ((TextView) view).getText().toString();
                        return text.matches(content);
                    }
                }
                return false;
            }
        };
    }
}

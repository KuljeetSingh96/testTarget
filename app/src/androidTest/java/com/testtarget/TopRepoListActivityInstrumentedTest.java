package com.testtarget;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.testtarget.ui.repo.TopRepoListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TopRepoListActivityInstrumentedTest {
    private int screenWaitingTime = 4500;
    @Rule
    public ActivityTestRule<TopRepoListActivity> mActivityTestRule =
            new ActivityTestRule<>(TopRepoListActivity.class);
    /**
     * click list item
     */
    @Test
    public void click_ListItemTest() {
        try {
            Thread.sleep(screenWaitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(1)).perform(click());
    }
}

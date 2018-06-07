package com.divitngoc.android.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.divitngoc.android.bakingapp.ui.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.contrib.RecyclerViewActions;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityIntentTest {


    private static final String RECIPE_ITEM = "Nutella Pie";

    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    /**
     * Clicks on a recycler view item and checks it opens up the DetailActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_OpensDetailActivity() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        // Checks that the DetailActivity opens with the correct name displayed
        onView(withText(RECIPE_ITEM)).check(matches(isDisplayed()));
    }

}

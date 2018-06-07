package com.divitngoc.android.bakingapp.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;
import com.divitngoc.android.bakingapp.widgets.WidgetUpdateIntentService;

public class DetailActivity extends AppCompatActivity implements StepMasterListFragment.OnPositionListener {

    private static final String CURRENT_POSITION_KEY = "currentPositionKey";

    private Bundle mBundle;
    private Recipe mRecipe;
    private boolean mTwoPane;

    // Keep track of the position
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        mBundle = null;
        mRecipe = null;
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            mBundle = intentThatStartedThisActivity.getExtras();
            mRecipe = intentThatStartedThisActivity.getParcelableExtra(ConstantUtils.RECIPE_KEY);
        }

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(CURRENT_POSITION_KEY);
        }

        if (getSupportActionBar() != null && mRecipe != null) {
            getSupportActionBar().setTitle(mRecipe.getName());
        }

        mTwoPane = checkIfTwoPaneExist();

        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(mBundle);
            fragmentManager.beginTransaction().add(R.id.ingredient_container, ingredientFragment).commit();

            mBundle.putBoolean(ConstantUtils.TWO_PANE_KEY, mTwoPane);
            StepMasterListFragment stepMasterListFragment = new StepMasterListFragment();
            stepMasterListFragment.setArguments(mBundle);
            fragmentManager.beginTransaction().add(R.id.step_list_container, stepMasterListFragment).commit();

            StepFragment stepFragment = new StepFragment();
            Bundle stepBundle = new Bundle();
            stepBundle.putParcelable(ConstantUtils.STEP_KEY, mRecipe.getStepsList().get(mPosition));
            stepFragment.setArguments(stepBundle);
            fragmentManager.beginTransaction().add(R.id.step_detail_container, stepFragment).commit();

        } else {
            setupViewPager();
        }

        startWidgetService();
    }

    private boolean checkIfTwoPaneExist() {
        return (findViewById(R.id.two_pane_linear_layout) != null);
    }

    private void setupViewPager() {
        RecipeDetailFragmentPagerAdapter adapter = new RecipeDetailFragmentPagerAdapter(this, getSupportFragmentManager(), mBundle);

        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPositionSelected(int position) {
        // Checking if the position selected isn't already selected and that it's on a tablet.
        if (mTwoPane && position != mPosition) {
            mPosition = position;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ConstantUtils.STEP_KEY, mRecipe.getStepsList().get(position));
            StepFragment stepFragmentSelected = new StepFragment();
            stepFragmentSelected.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.step_detail_container, stepFragmentSelected).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION_KEY, mPosition);
    }

    /**
     * Update the Widget to the last recipe that the user has seen via WidgetUpdateIntentService.
     */
    void startWidgetService() {
        Intent intent = new Intent(this, WidgetUpdateIntentService.class);
        intent.setAction(WidgetUpdateIntentService.WIDGET_UPDATE_ACTION);
        intent.putExtra(ConstantUtils.RECIPE_KEY, mRecipe);
        startService(intent);
    }

}

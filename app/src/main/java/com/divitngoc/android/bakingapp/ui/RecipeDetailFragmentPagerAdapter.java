package com.divitngoc.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Recipe;

public class RecipeDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Bundle mBundle;

    public RecipeDetailFragmentPagerAdapter(Context context, FragmentManager fm, Bundle bundle) {
        super(fm);
        mContext = context;
        mBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                IngredientFragment ingredientFragment = new IngredientFragment();
                ingredientFragment.setArguments(mBundle);
                return ingredientFragment;
            default:
                StepMasterListFragment stepMasterListFragment = new StepMasterListFragment();
                stepMasterListFragment.setArguments(mBundle);
                return stepMasterListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.ingredient);
            default:
                return mContext.getString(R.string.steps);
        }
    }
}

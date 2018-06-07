package com.divitngoc.android.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.model.Step;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    private Step mCurrentStep;
    private Step mPreviousStep;
    private Step mNextStep;
    private List<Step> mStepList;
    private int currentPosition;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    @BindView(R.id.step_detail_previous_button)
    Button mLeftButton;
    @BindView(R.id.step_detail_next_button)
    Button mRightButton;

    private static final String POSITION_KEY = "currentPosition";
    private static final String STEP_BUNDLE_KEY = "currentStepBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);
        ButterKnife.bind(this);
        bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(STEP_BUNDLE_KEY);
            Recipe recipe = bundle.getParcelable(ConstantUtils.RECIPE_KEY);
            mStepList = recipe.getStepsList();
            currentPosition = savedInstanceState.getInt(POSITION_KEY);
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                bundle = intent.getExtras();
                Recipe recipe = bundle.getParcelable(ConstantUtils.RECIPE_KEY);
                mStepList = recipe.getStepsList();
                currentPosition = bundle.getInt(ConstantUtils.POSITION_STEP_KEY);
            }
        }
        mCurrentStep = mStepList.get(currentPosition);
        if (!TextUtils.isEmpty(mCurrentStep.getShortDescription())) {
            getSupportActionBar().setTitle(mCurrentStep.getShortDescription());
        }

        StepFragment stepFragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.STEP_KEY, mCurrentStep);
        stepFragment.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
        initialiseButtons();
        setupButtons();
    }

    private void initialiseButtons() {
        if (currentPosition == 0) {
            mLeftButton.setVisibility(View.INVISIBLE);
        } else {
            mPreviousStep = mStepList.get(currentPosition - 1);
            mLeftButton.setText(mPreviousStep.getShortDescription());
        }

        if (currentPosition == mStepList.size() - 1) {
            mRightButton.setVisibility(View.INVISIBLE);
        } else {
            mNextStep = mStepList.get(currentPosition + 1);
            mRightButton.setText(mNextStep.getShortDescription());
        }
    }

    /**
     * Setting listeners for buttons
     * When clicked, .replace is called, actionbar title changes,
     * button changes and current position changes accordingly
     */
    private void setupButtons() {
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition != 0) {
                    mLeftButton.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    mPreviousStep = mStepList.get(currentPosition - 1);
                    getSupportActionBar().setTitle(mPreviousStep.getShortDescription());
                    if (currentPosition > 1) {
                        mLeftButton.setText(mStepList.get(currentPosition - 2).getShortDescription());
                        if (mRightButton.getVisibility() == View.INVISIBLE) {
                            mRightButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mLeftButton.setVisibility(View.INVISIBLE);
                    }
                    mRightButton.setText(mStepList.get(currentPosition).getShortDescription());

                    bundle.putParcelable(ConstantUtils.STEP_KEY, mPreviousStep);
                    StepFragment previousStepFragment = new StepFragment();
                    previousStepFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.step_container, previousStepFragment).commit();
                    currentPosition -= 1;
                } else {
                    mLeftButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition != mStepList.size() - 1) {
                    mRightButton.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    mNextStep = mStepList.get(currentPosition + 1);
                    getSupportActionBar().setTitle(mNextStep.getShortDescription());
                    if (currentPosition != mStepList.size() - 2) {
                        mRightButton.setText(mStepList.get(currentPosition + 2).getShortDescription());
                        if (mLeftButton.getVisibility() == View.INVISIBLE) {
                            mLeftButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mRightButton.setVisibility(View.INVISIBLE);
                    }
                    mLeftButton.setText(mStepList.get(currentPosition).getShortDescription());

                    bundle.putParcelable(ConstantUtils.STEP_KEY, mNextStep);
                    StepFragment nextStepFragment = new StepFragment();
                    nextStepFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.step_container, nextStepFragment).commit();
                    currentPosition++;
                } else {
                    mRightButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, currentPosition);
        outState.putBundle(STEP_BUNDLE_KEY, bundle);
    }
}

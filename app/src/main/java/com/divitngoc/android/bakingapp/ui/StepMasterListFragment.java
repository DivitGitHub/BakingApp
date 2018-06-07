package com.divitngoc.android.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.model.Step;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepMasterListFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler {
    @BindView(R.id.step_recycler_view)
    RecyclerView recyclerView;

    private RecipeAdapter mAdapter;
    private List<Step> mStepList;
    Recipe recipe;
    private boolean mTwoPane = false;
    OnPositionListener mCallBack;

    public interface OnPositionListener {
        void onPositionSelected(int position);
    }

    public StepMasterListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnPositionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnPositionListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.step_list, container, false);
        ButterKnife.bind(this, rootView);
        mAdapter = new RecipeAdapter(getContext(), this, RecipeAdapter.TYPE_STEP);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ConstantUtils.RECIPE_KEY)) {
                recipe = bundle.getParcelable(ConstantUtils.RECIPE_KEY);
                mStepList = recipe.getStepsList();
            }
            if (bundle.containsKey(ConstantUtils.TWO_PANE_KEY)) {
                mTwoPane = bundle.getBoolean(ConstantUtils.TWO_PANE_KEY);
            }
        }

        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        mAdapter.setStepListData(mStepList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(int position) {
        if (mTwoPane) {
            mCallBack.onPositionSelected(position);
        } else {
            Intent startStepActivity = new Intent(getActivity(), StepActivity.class);
            startStepActivity.putExtra(ConstantUtils.RECIPE_KEY, recipe);
            startStepActivity.putExtra(ConstantUtils.POSITION_STEP_KEY, position);
            getActivity().startActivity(startStepActivity);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
    }
}

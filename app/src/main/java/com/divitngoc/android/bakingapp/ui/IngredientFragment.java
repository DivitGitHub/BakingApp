package com.divitngoc.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Ingredient;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    @BindView(R.id.ingredient_recycler_view)
    RecyclerView mIngredientRecyclerView;

    List<Ingredient> mIngredientList;
    RecipeAdapter mAdapter;

    public IngredientFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_list, container, false);
        ButterKnife.bind(this, rootView);

        mAdapter = new RecipeAdapter(getActivity(), null, RecipeAdapter.TYPE_INGREDIENT);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ConstantUtils.RECIPE_KEY)) {
                Recipe recipe = bundle.getParcelable(ConstantUtils.RECIPE_KEY);
                mIngredientList = recipe.getIngredientsList();
            }
        }

        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        mAdapter.setIngredientListData(mIngredientList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mIngredientRecyclerView.setLayoutManager(linearLayoutManager);
        mIngredientRecyclerView.setHasFixedSize(true);
        mIngredientRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}

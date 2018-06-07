package com.divitngoc.android.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.model.Step;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;
import com.divitngoc.android.bakingapp.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String LOG_TAG = RecipeListActivity.class.getSimpleName();

    // Key for onSavedInstanceState
    private static final String RECIPE_LIST_KEY = "recipe";

    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;

    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        mRecipeAdapter = new RecipeAdapter(this, this, RecipeAdapter.TYPE_RECIPE);
        setupRecipeData(savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecipeData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_KEY);
        } else {
            try {
                mRecipeList = JsonUtils.getRecipeDataFromJson(JsonUtils.getJsonStringFromTxt(this));
            } catch (JSONException e) {
                Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, e.toString());
            }
        }
        mRecipeAdapter.setRecipeListData(mRecipeList);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent startDetailViewActivity = new Intent(this, DetailActivity.class);
        startDetailViewActivity.putExtra(ConstantUtils.RECIPE_KEY, recipe);
        startActivity(startDetailViewActivity);
    }

    @Override
    public void onClick(int position) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST_KEY, mRecipeList);
    }
}

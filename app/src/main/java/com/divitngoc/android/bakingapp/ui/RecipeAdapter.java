package com.divitngoc.android.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Ingredient;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Recipe> mRecipeData;
    private List<Ingredient> mIngredientData;
    private List<Step> mStepData;
    private Context mContext;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public final static int TYPE_RECIPE = 0, TYPE_INGREDIENT = 1, TYPE_STEP = 2;

    // The type of data wanted when adapter is created
    private int type;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);

        void onClick(int position);
    }

    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler clickHandler, int TYPE) {
        mContext = context;
        mClickHandler = clickHandler;
        type = TYPE;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_title_list_item)
        TextView tvTitle;
        @BindView(R.id.tv_recipe_serving_list_item)
        TextView tvServing;
        @BindView(R.id.grid_image_list_item)
        ImageView ivImage;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeData.get(adapterPosition);
            if (mClickHandler != null) {
                mClickHandler.onClick(recipe);
            }
        }
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_layout_ingredient_list_item)
        LinearLayout linearLayoutIngredient;
        @BindView(R.id.ingredient_name_item)
        TextView tvIngredientName;
        @BindView(R.id.quantity_and_measure_item)
        TextView tvQuantityMeasure;

        public IngredientAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_title)
        TextView tvStepTitle;
        @BindView(R.id.v_step_line_bottom)
        View vStepLinebottom;
        @BindView(R.id.v_step_line_top)
        View vStepLineTop;

        public StepAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            if (mClickHandler != null) {
                mClickHandler.onClick(adapterPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case TYPE_RECIPE:
                return TYPE_RECIPE;
            case TYPE_INGREDIENT:
                return TYPE_INGREDIENT;
            case TYPE_STEP:
                return TYPE_STEP;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem;
        View view;
        RecyclerView.ViewHolder viewHolder = null;

        switch (type) {
            case TYPE_RECIPE:
                layoutIdForListItem = R.layout.recipe_list_item;
                view = inflater.inflate(layoutIdForListItem, parent, false);
                viewHolder = new RecipeAdapterViewHolder(view);
                break;
            case TYPE_INGREDIENT:
                layoutIdForListItem = R.layout.ingredient_list_item;
                view = inflater.inflate(layoutIdForListItem, parent, false);
                viewHolder = new IngredientAdapterViewHolder(view);
                break;
            case TYPE_STEP:
                layoutIdForListItem = R.layout.step_master_list_item;
                view = inflater.inflate(layoutIdForListItem, parent, false);
                viewHolder = new StepAdapterViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // int typeView = getItemViewType(position);

        switch (type) {
            case TYPE_RECIPE:
                RecipeAdapterViewHolder recipeVH = (RecipeAdapterViewHolder) holder;
                Recipe recipe = mRecipeData.get(position);
                String name = recipe.getName();
                String servings = mContext.getString(R.string.servings) + " " + Integer.toString(recipe.getServings());
                String image = recipe.getImage();

                recipeVH.tvTitle.setText(name);
                recipeVH.tvServing.setText(servings);

                if (!TextUtils.isEmpty(image)) {
                    Picasso.with(mContext)
                            .load(image)
                            .error(R.drawable.recipe_list_image)
                            .placeholder(R.drawable.recipe_list_image)
                            .into(recipeVH.ivImage);
                } else {
                    recipeVH.ivImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.recipe_list_image));
                }

                break;
            case TYPE_INGREDIENT:
                IngredientAdapterViewHolder ingredientVH = (IngredientAdapterViewHolder) holder;
                Ingredient ingredient = mIngredientData.get(position);
                String ingredientName = ingredient.getIngredient();
                ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);

                String measure = ingredient.getMeasure().toLowerCase();
                double quantity = ingredient.getQuantity();

                String quantityMeasure;
                if (quantity % 1 == 0) {
                    quantityMeasure = (int) quantity + " " + measure;
                } else {
                    quantityMeasure = quantity + " " + measure;
                }

                // Alternating the background color
                if (position % 2 == 0) {
                    ingredientVH.tvIngredientName.setBackgroundColor(mContext.getResources().getColor(R.color.yellow200));
                    ingredientVH.tvQuantityMeasure.setBackgroundColor(mContext.getResources().getColor(R.color.yellow200));
                } else {
                    ingredientVH.linearLayoutIngredient.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    ingredientVH.tvQuantityMeasure.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                ingredientVH.tvIngredientName.setText(ingredientName);
                ingredientVH.tvQuantityMeasure.setText(quantityMeasure);

                break;
            case TYPE_STEP:
                StepAdapterViewHolder stepVH = (StepAdapterViewHolder) holder;



                if (position == 0) {
                    stepVH.vStepLineTop.setVisibility(View.INVISIBLE);
                } else if (position == mStepData.size() - 1) {
                    stepVH.vStepLinebottom.setVisibility(View.INVISIBLE);
                }

                Step step = mStepData.get(position);
                stepVH.tvStepTitle.setText(position + ". " + step.getShortDescription());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeData != null) {
            return mRecipeData.size();
        } else if (mIngredientData != null) {
            return mIngredientData.size();
        } else if (mStepData != null) {
            return mStepData.size();
        } else {
            return 0;
        }
    }

    public void setRecipeListData(ArrayList<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }

    public void setIngredientListData(List<Ingredient> ingredientData) {
        mIngredientData = ingredientData;
        notifyDataSetChanged();
    }

    public void setStepListData(List<Step> stepData) {
        mStepData = stepData;
        notifyDataSetChanged();
    }

}

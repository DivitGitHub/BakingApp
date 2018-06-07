package com.divitngoc.android.bakingapp.utils;

import android.content.Context;
import android.util.Log;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Ingredient;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // JSON key for recipe
    private static final String NAME_KEY = "name";
    private static final String SERVINGS_KEY = "servings";
    private static final String IMAGE_KEY = "image";

    // JSON key for ingredient
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String INGREDIENTS_QUANTITY_KEY = "quantity";
    private static final String INGREDIENTS_MEASURE_KEY = "measure";
    private static final String INGREDIENTS_NAME_KEY = "ingredient";

    // JSON key for steps
    private static final String STEPS_KEY = "steps";
    private static final String STEPS_ID_KEY = "id";
    private static final String STEPS_SHORT_DESC = "shortDescription";
    private static final String STEPS_DESC = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    private JsonUtils() {
    }

    /**
     * Extract the data from JSON string and store recipe object in a List
     *
     * @param jsonStr a string representing JSON format
     * @return ArrayList<Recipe>
     * @throws JSONException
     */
    public static ArrayList<Recipe> getRecipeDataFromJson(String jsonStr) throws JSONException {
        JSONArray baseResponse = new JSONArray(jsonStr);
        ArrayList<Recipe> recipeList = new ArrayList<>();

        // Getting recipe objects
        for (int i = 0; i < baseResponse.length(); i++) {
            JSONObject currentRecipe = baseResponse.getJSONObject(i);

            String name = currentRecipe.optString(NAME_KEY);
            int servings = currentRecipe.optInt(SERVINGS_KEY);
            String image = currentRecipe.optString(IMAGE_KEY);

            // Getting ingredient objects
            JSONArray ingredientJSONArray = currentRecipe.getJSONArray(INGREDIENTS_KEY);
            List<Ingredient> ingredientsList = new ArrayList<>();
            for (int j = 0; j < ingredientJSONArray.length(); j++) {
                JSONObject currentIngredient = ingredientJSONArray.getJSONObject(j);
                double quantity = currentIngredient.optDouble(INGREDIENTS_QUANTITY_KEY);
                String measure = currentIngredient.optString(INGREDIENTS_MEASURE_KEY);
                String ingredient = currentIngredient.optString(INGREDIENTS_NAME_KEY);

                ingredientsList.add(new Ingredient(quantity, measure, ingredient));
            }

            // Getting step objects
            JSONArray stepsJSONArray = currentRecipe.getJSONArray(STEPS_KEY);
            List<Step> stepsList = new ArrayList<>();
            for (int k = 0; k < stepsJSONArray.length(); k++) {
                JSONObject currentStep = stepsJSONArray.getJSONObject(k);
                int id = currentStep.optInt(STEPS_ID_KEY);
                String shortDesc = currentStep.optString(STEPS_SHORT_DESC);
                String desc = currentStep.optString(STEPS_DESC);
                String videoUrl = currentStep.optString(STEPS_VIDEO_URL);
                String thumbnailUrl = currentStep.getString(STEPS_THUMBNAIL_URL);

                stepsList.add(new Step(id, shortDesc, desc, videoUrl, thumbnailUrl));
            }
            recipeList.add(new Recipe(name, ingredientsList, stepsList, servings, image));
        }

        return recipeList;
    }

    public static String getJsonStringFromTxt(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.recipe_list);
        java.util.Scanner s = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

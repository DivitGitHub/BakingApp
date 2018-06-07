package com.divitngoc.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private String name;
    private List<Ingredient> ingredientsList;
    private List<Step> stepsList;
    private int servings;
    private String image;

    public Recipe(String name, List<Ingredient> ingredientsList, List<Step> stepsList, int servings, String image) {
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in) {
        name = in.readString();
        ingredientsList = new ArrayList<>();
        in.readTypedList(ingredientsList, Ingredient.CREATOR);
        stepsList = new ArrayList<>();
        in.readTypedList(stepsList, Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(ingredientsList);
        parcel.writeTypedList(stepsList);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }

    };

    public String getName() {
        return name;
    }

    public void setIngredientsList(List<Ingredient> list) {
        ingredientsList = list;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public List<Step> getStepsList() {
        return stepsList;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}

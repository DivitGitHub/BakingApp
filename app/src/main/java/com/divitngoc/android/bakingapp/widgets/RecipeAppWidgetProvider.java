package com.divitngoc.android.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Ingredient;
import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.ui.RecipeListActivity;

import java.util.List;

public class RecipeAppWidgetProvider extends AppWidgetProvider {

    public static List<Ingredient> mIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetIds[], Recipe recipe) {

        mIngredients = recipe.getIngredientsList();
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
            views.setTextViewText(R.id.appwidget_text, recipe.getName());
            views.setRemoteAdapter(R.id.appwidget_list, intent);
            ComponentName component = new ComponentName(context, RecipeAppWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);
            appWidgetManager.updateAppWidget(component, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


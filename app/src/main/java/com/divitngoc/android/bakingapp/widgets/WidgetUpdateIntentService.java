package com.divitngoc.android.bakingapp.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divitngoc.android.bakingapp.model.Recipe;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;

public class WidgetUpdateIntentService extends IntentService {

    public static final String WIDGET_UPDATE_ACTION = "com.divitngoc.android.bakingapp.widgets.update_widget";

    public WidgetUpdateIntentService() {
        super("WidgetUpdateIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction().equals(WIDGET_UPDATE_ACTION)) {
            Recipe recipe = null;
            if (intent.hasExtra(ConstantUtils.RECIPE_KEY)) {
                recipe = intent.getParcelableExtra(ConstantUtils.RECIPE_KEY);
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeAppWidgetProvider.class));
            RecipeAppWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds, recipe);
        }
    }
}

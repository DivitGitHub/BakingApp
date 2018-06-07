package com.divitngoc.android.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Ingredient;
import com.divitngoc.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new RecipeRemoteViewsFactory(this.getApplicationContext(), intent));
    }

    public class RecipeRemoteViewsFactory implements RemoteViewsFactory {
        private Context mContext;
        private Intent mIntent;
        private List<Ingredient> ingredientList;

        public RecipeRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mIntent = intent;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientList = RecipeAppWidgetProvider.mIngredients;
        }

        @Override
        public void onDestroy() {
            ingredientList.clear();
        }

        @Override
        public int getCount() {
            return ingredientList != null ? ingredientList.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            String ingredientName = ingredientList.get(i).getIngredient();
            ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);
            views.setTextViewText(R.id.widget_name_item, ingredientName);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}

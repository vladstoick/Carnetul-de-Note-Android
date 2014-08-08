package com.stoicavlad.carnet.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.Utility;
import com.stoicavlad.carnet.data.provider.CarnetContract;

/**
 * Implementation of App Widget functionality.
 */
public class GeneralWidget extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_general);
        //absente
        CursorLoader absenteCursorLoader = new CursorLoader(
                context,
                CarnetContract.AbsentaEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Cursor absenteCursor = absenteCursorLoader.loadInBackground();
        String absente_text = context.getString(R.string.absente_section) + " : " + absenteCursor.getCount();
        views.setTextViewText(R.id.absente,  absente_text);
        //medii
        CursorLoader medieCursorLoader = new android.content.CursorLoader(
                context,
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS_MEDIE,
                null,
                null,
                null
        );
        Cursor medieCursor = medieCursorLoader.loadInBackground();
        SharedPreferences preferences = context.getSharedPreferences("appPref",
                Context.MODE_PRIVATE);
        int purtare = preferences.getInt("PURTARE_TAG",9);
        double medie = Utility.getMedieGeneralaFromCursor(medieCursor, purtare);
        String medie_text;
        if(medie>0){
            medie_text = context.getString(R.string.medie) + " : " + medie;
        } else{
            medie_text = context.getString(R.string.medie) + " : - ";
        }
        views.setTextViewText(R.id.medie,  medie_text);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}



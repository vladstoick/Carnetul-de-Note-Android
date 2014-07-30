package com.stoicavlad.carnet.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.OrmliteSqlHelper;
import com.stoicavlad.carnet.data.api.AbsenteDatabase;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.AbsentaUtility;

/**
 * Implementation of App Widget functionality.
 */
public class GeneralWidget extends AppWidgetProvider {

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
        OrmliteSqlHelper ormliteSqlHelper = new OrmliteSqlHelper(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_general);
        //absente
        AbsenteDatabase absenteDatabase = new AbsenteDatabase(ormliteSqlHelper);
        AbsentaUtility[] absente = absenteDatabase.getAbsente();
        String absente_text = context.getString(R.string.absente_section) + " : " + absente.length;
        views.setTextViewText(R.id.absente,  absente_text);
        //medii
        MateriiDatabase materiiDatabase = new MateriiDatabase(ormliteSqlHelper);
        double medie = materiiDatabase.getMedieGenerala();
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



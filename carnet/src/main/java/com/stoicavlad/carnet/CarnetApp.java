package com.stoicavlad.carnet;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.stoicavlad.carnet.ui.widget.GeneralWidget;

/**
 * Created by Vlad on 1/26/14.
 */
public class CarnetApp extends Application {

    public static CarnetApp get(Context context) {
        return (CarnetApp) context.getApplicationContext();
    }

    public void updateWidget(){
        Intent intent = new Intent(this,GeneralWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
    // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
    // since it seems the onUpdate() is only fired on that:
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        if(appWidgetManager != null){
            int ids[] = appWidgetManager
                    .getAppWidgetIds(new ComponentName(this, GeneralWidget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            sendBroadcast(intent);
        }
    }
}

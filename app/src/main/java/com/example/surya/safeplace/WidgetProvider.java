package com.example.surya.safeplace;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            Intent it=new Intent(context,DistressAlarm.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,it,0);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.dcall,pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}

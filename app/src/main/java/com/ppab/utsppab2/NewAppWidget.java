package com.ppab.utsppab2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider implements SensorEventListener {

    private static SensorManager sensorManager;
    private static Sensor humid;
    private static Sensor temp;
    String temP,Humid;

    private static final String mSharedPreferences = "com.ppab.ppab2pertemuan2";
    private static final String COUNT_KEY = "count";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(mSharedPreferences, 0);
        int count = prefs.getInt(COUNT_KEY+appWidgetId, 0);
        count++;

        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putInt(COUNT_KEY+appWidgetId, count);
        prefEditor.apply();

        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        views.setTextViewText(R.id.wdHumid, Humid);
//        views.setTextViewText(R.id.wdTemp, temP);
//        views.setTextViewText(R.id.appwidget_id, String.valueOf(appWidgetId));
        views.setTextViewText(R.id.appwidget_update, dateString);
        views.setTextViewText(R.id.appwidget_count, ""+formattedDate);
        views.setTextViewText(R.id.value_temp, temP);

        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);


        int[] idArray = new int[]{appWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.button_update, pendingIntent);
        views.setOnClickPendingIntent(R.id.check_temp, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        humid = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        sensorManager.registerListener(this, temp, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, humid, sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                temP = String.format("Temperature: %1$.2f C", currentValue);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                Humid = String.format("Humidity: %1$.2f", currentValue);
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
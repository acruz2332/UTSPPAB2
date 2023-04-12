package com.ppab.utsppab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class dashboardActivity extends AppCompatActivity implements SensorEventListener {
    private ArrayList<destination> destinations;
    private TextView sudah, belum, tvHumidity, tvTemperature;
    private int count;

    private SensorManager sensorManager;
    private Sensor humid, temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle extras = getIntent().getExtras();
        destinations = extras.getParcelableArrayList("destinations");

        sudah = findViewById(R.id.sudah);
        belum = findViewById(R.id.belum);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvTemperature = findViewById(R.id.tvTemp);
        count = 0;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        humid = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        belum.setText(""+destinations.size());
        for (destination des : destinations){
            if (des.isVisited()){
                count += 1;
            }
        }
        sudah.setText(""+count);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (temp != null) {
            sensorManager.registerListener(this, temp, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humid != null) {
            sensorManager.registerListener(this, humid, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                tvTemperature.setText(String.format("%1$.2f C", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                tvHumidity.setText(String.format("%1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


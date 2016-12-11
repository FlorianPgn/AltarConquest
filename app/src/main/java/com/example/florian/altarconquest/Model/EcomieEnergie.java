package com.example.florian.altarconquest.Model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.WindowManager;

import com.example.florian.altarconquest.R;
import com.example.florian.altarconquest.View.EcranJeu;

public class EcomieEnergie implements SensorEventListener {

    public EcranJeu context;
    public SensorManager sensorManager;
    public Sensor gyroSensor;

    public EcomieEnergie(EcranJeu pContext) {

        context = pContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Log.i("eco", "eco créé");

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float value = event.values[1];

            if (value <= -3) {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.screenBrightness = 0;
                context.getWindow().setAttributes(lp);
                context.imageEconomie.setBackgroundResource(R.drawable.image_eco);

            } else {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.screenBrightness = -1;
                context.getWindow().setAttributes(lp);
                context.imageEconomie.setBackgroundResource(R.drawable.transparent);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void start() {
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }
}
package com.example.sensorapplication;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Acceleration extends AppCompatActivity implements SensorEventListener {
    private LineChart chart;
    private List<Entry> entries;
    private LineDataSet dataSet;
    private LineData lineData;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceleration);

        chart = findViewById(R.id.chart);

        // Initialize entries list and line dataset
        entries = new ArrayList<>();
        dataSet = new LineDataSet(entries, "Acceleration");

        // Add some initial data entries (for example, 0 values)
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, 0));
        }
        // Configure line dataset
        dataSet.setColor(Color.RED);
       dataSet.setDrawCircles(false);
      dataSet.setDrawValues(false);

        // Configure line chart
        lineData = new LineData(dataSet);
        chart.setData(lineData);
        Description description = new Description();
        description.setText("Acceleration");
        chart.setDescription(description);
        chart.getXAxis().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.invalidate();

        // Get the accelerometer sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener to avoid resource leaks
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Get acceleration values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate the magnitude of acceleration
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

            // Add the data entry to the chart
            entries.add(new Entry(entries.size(), magnitude));

            // Notify the chart that the data has changed
            dataSet.notifyDataSetChanged();
            lineData.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Empty method, not used
    }
}

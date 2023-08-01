package com.example.sensorapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sensorapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     private TextView sensorCountTextView;
     private ListView sensorListView;
     Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorCountTextView = findViewById(R.id.textview1);
        sensorListView= findViewById(R.id.listview);
        btn1= findViewById(R.id.btn1);
        btn2= findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lightSensor.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Acceleration.class);
                startActivity(intent);
            }
        });
        // Get the SensorManager instance
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get the list of available sensors
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Get the number of sensors
        int sensorCount = ((List<?>) sensorList).size();

        // Display the sensor count in the TextView
        sensorCountTextView.setText("Number of Sensors: " + sensorCount);

        // get the sensors names
        List<String> sensorNames = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            sensorNames.add(sensor.getName());
        }

        // Create an ArrayAdapter to display the sensor names in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensorNames);

        // Set the ArrayAdapter as the adapter for the ListView
        sensorListView.setAdapter(adapter);

    }
}
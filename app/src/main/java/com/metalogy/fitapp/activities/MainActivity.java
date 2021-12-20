package com.metalogy.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.metalogy.fitapp.R;

public class MainActivity extends AppCompatActivity {
    Button btnStopwatch;
    Button btnExercise;
    Button btnWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnExercise = findViewById(R.id.btnExercise);
        btnWorkout = findViewById(R.id.btnWorkout);
        btnStopwatch = findViewById(R.id.btnStopwatch);

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });

        btnStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VoiceStopwachActivity.class));
            }
        });


    }

}
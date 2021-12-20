package com.metalogy.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.metalogy.fitapp.R;

public class SelectionActivity extends AppCompatActivity {

    Button btnPushup;
    Button btnSquat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        btnPushup = findViewById(R.id.btnPushup);
        btnSquat = findViewById(R.id.btnSquats);

        btnPushup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, PushUpActivity.class);
                startActivity(intent);

            }
        });

        btnSquat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, SquatActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
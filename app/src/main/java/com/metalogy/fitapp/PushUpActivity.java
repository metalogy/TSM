package com.metalogy.fitapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PushUpActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    int curr;
    long pushedDownTime;
    long pushedUpTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);

        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);

        textView.setText("0");

    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                pushedDownTime = SystemClock.elapsedRealtime();
                break;
            case MotionEvent.ACTION_UP:
                pushedUpTime = SystemClock.elapsedRealtime();
                long pressedTime = pushedUpTime - pushedDownTime;

                if (pressedTime >= 2000) {
                    curr = Integer.parseInt(textView.getText().toString());
                    curr++;
                    textView.setText(String.valueOf(curr));
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

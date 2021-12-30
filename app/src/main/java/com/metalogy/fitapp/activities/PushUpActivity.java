package com.metalogy.fitapp.activities;

import static com.metalogy.fitapp.constants.Constants.PUSH_UPS_DOWN_TIME_MILLIS;

import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.metalogy.fitapp.R;

public class PushUpActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;

    TextView tvPushUpsCounter;

    int pushUpCounter;
    long pushedDownTime;
    long pushedUpTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);

        tvPushUpsCounter = findViewById(R.id.tvPushUpsCounter);

        textToSpeech = new TextToSpeech(this, this);

        tvPushUpsCounter.setText("0");
        pushUpCounter = 0;
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

                if (pressedTime >= PUSH_UPS_DOWN_TIME_MILLIS) {
                    pushUpCounter++;
                    tvPushUpsCounter.setText(String.valueOf(pushUpCounter));
                    textToSpeech.speak(
                            String.valueOf(pushUpCounter), TextToSpeech.QUEUE_ADD, null
                    );
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

    @Override
    public void onInit(int status) {

    }
}

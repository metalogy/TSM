package com.metalogy.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.metalogy.fitapp.R;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;

import java.util.List;

public class VoiceStopwatchActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener, OnDSListener {
    Button btnStart, btnStop;
    Chronometer chronometer;

    private DroidSpeech droidSpeech;
    private TextView finalSpeechResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_stopwatch);

        droidSpeech = new DroidSpeech(this, getFragmentManager());
        droidSpeech.setOnDroidSpeechListener(this);
        finalSpeechResult = findViewById(R.id.finalSpeechResult);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        btnStart = findViewById(R.id.btnStartVoiceRecognition);
        btnStart.setOnClickListener(this);

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                droidSpeech.startDroidSpeechRecognition();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droidSpeech.closeDroidSpeechOperations();
            }
        });
    }

    public void startChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    public void stopChronometer() {
        chronometer.stop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages) {
    }


    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue) {
    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult) {
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult) {
        this.finalSpeechResult.setText(finalSpeechResult);

        if (finalSpeechResult.equals("start")) {
            startChronometer();
        } else if (finalSpeechResult.equals("stop")) {
            droidSpeech.closeDroidSpeechOperations(); //#TODO bug nie działa
            stopChronometer();
        }
    }

    @Override
    public void onDroidSpeechClosedByUser() {
        droidSpeech.closeDroidSpeechOperations();

    }

    @Override
    public void onDroidSpeechError(String errorMsg) {
    }


    @Override
    public void onInit(int status) {
    }

    @Override
    public void onClick(View v) {
    }
}

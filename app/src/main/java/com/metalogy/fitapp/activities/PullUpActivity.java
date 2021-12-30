package com.metalogy.fitapp.activities;

import static com.metalogy.fitapp.constants.Constants.DOWN_THRESHOLD_PULL_UP;
import static com.metalogy.fitapp.constants.Constants.POINTS_FOR_WATCHING_AD;
import static com.metalogy.fitapp.constants.Constants.PULL_UPS_VALUES_BUFFER_SIZE;
import static com.metalogy.fitapp.constants.Constants.SHARED_PREFS_POINTS;
import static com.metalogy.fitapp.constants.Constants.UP_THRESHOLD_PULL_UP;
import static com.metalogy.fitapp.enums.PushPullEnum.DOWN;
import static com.metalogy.fitapp.enums.PushPullEnum.NEUTRAL;
import static com.metalogy.fitapp.enums.PushPullEnum.UP;

import static java.lang.Math.abs;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metalogy.fitapp.R;
import com.metalogy.fitapp.enums.PushPullEnum;

import java.util.ArrayDeque;

public class PullUpActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener accelerometerEventListener;
    private TextToSpeech textToSpeech;

    private TextView tvPullUpCounter;

    private PushPullEnum currentState = NEUTRAL;
    private PushPullEnum previousState;

    private ArrayDeque<Float> valuesOverTimeY = new ArrayDeque<>();
    private ArrayDeque<Float> valuesOverTimeZ = new ArrayDeque<>();

    private int pullUpCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_up);

        tvPullUpCounter = findViewById(R.id.tvPullUpCounter);

        textToSpeech = new TextToSpeech(this, this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            Toast.makeText(this, "The device has no accelerometer!", Toast.LENGTH_SHORT).show();
        }

        accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float Y = event.values[1];
                float Z = event.values[2];
                valuesOverTimeY = updateVal(valuesOverTimeY, Y);
                valuesOverTimeZ = updateVal(valuesOverTimeZ, Z);
                double meanAccChangeY = calculateMeanAcc(valuesOverTimeY);
                double meanAccChangeZ = calculateMeanAcc(valuesOverTimeZ);
                checkPosition(meanAccChangeY, meanAccChangeZ);
                checkIfPulledUp();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerEventListener, accelerometer, SensorManager.SENSOR_STATUS_ACCURACY_LOW, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerEventListener);
    }

    private ArrayDeque<Float> updateVal(ArrayDeque<Float> valuesOverTime, float newValue) {
        if (valuesOverTime.size() > PULL_UPS_VALUES_BUFFER_SIZE) {
            valuesOverTime.clear();
        }
        valuesOverTime.add(newValue);
        return valuesOverTime;
    }

    private Double calculateMeanAcc(ArrayDeque<Float> valuesOverTime) {
        if (valuesOverTime.size() == PULL_UPS_VALUES_BUFFER_SIZE) {
            return valuesOverTime.stream().mapToDouble(val -> val).average().orElse(0.0);
        }
        return 0.0;
    }

    private void checkPosition(Double meanY, Double meanZ) {
        double mean;

        if (abs(meanY) > abs(meanZ)) {
            mean = meanY;
        } else {
            mean = meanZ;
        }

        if (mean > DOWN_THRESHOLD_PULL_UP) {
            previousState = currentState;
            currentState = DOWN;
        } else if (mean <= UP_THRESHOLD_PULL_UP) {
            previousState = currentState;
            currentState = UP;
        }
    }

    private void checkIfPulledUp() {
        if (previousState == UP && currentState == DOWN) {
            pullUpCounter++;
            tvPullUpCounter.setText("" + pullUpCounter);
            textToSpeech.speak(
                    String.valueOf(pullUpCounter), TextToSpeech.QUEUE_ADD, null
            );
        }
    }

    @Override
    public void onInit(int status) {

    }
}
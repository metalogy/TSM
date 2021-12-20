package com.metalogy.fitapp.activities;

import static com.metalogy.fitapp.constants.constants.DOWN_THRESHOLD_SQUATS;
import static com.metalogy.fitapp.constants.constants.SQUATS_VALUES_BUFFER_SIZE;
import static com.metalogy.fitapp.constants.constants.UP_THRESHOLD_SQUATS;
import static com.metalogy.fitapp.enums.pushPullEnum.DOWN;
import static com.metalogy.fitapp.enums.pushPullEnum.NEUTRAL;
import static com.metalogy.fitapp.enums.pushPullEnum.UP;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import com.metalogy.fitapp.R;
import com.metalogy.fitapp.enums.pushPullEnum;

import java.util.ArrayDeque;

public class SquatActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener accelerometerEventListener;
    private TextToSpeech textToSpeech;

    private TextView tvSquatCounter;

    private pushPullEnum currentState = NEUTRAL;
    private pushPullEnum previousState;

    private ArrayDeque<Float> valuesOverTimeY = new ArrayDeque<Float>();
    private ArrayDeque<Float> valuesOverTimeZ = new ArrayDeque<Float>();

    private int squatCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);

        tvSquatCounter = findViewById(R.id.tvSquatCounter);

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
                checkIfSquated();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                ;

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
        if (valuesOverTime.size() > SQUATS_VALUES_BUFFER_SIZE) {
            valuesOverTime.clear();
        }
        valuesOverTime.add(newValue);
        return valuesOverTime;
    }

    private Double calculateMeanAcc(ArrayDeque<Float> valuesOverTime) {
        if (valuesOverTime.size() == SQUATS_VALUES_BUFFER_SIZE) {
            return valuesOverTime.stream().mapToDouble(val -> val).average().orElse(0.0);
        }
        return 0.0;
    }

    private void checkPosition(Double meanY, Double meanZ) {
        Double mean;

        if (abs(meanY) > abs(meanZ)) {
            mean = meanY;
        } else {
            mean = meanZ;
        }

        if (mean > DOWN_THRESHOLD_SQUATS) {
            previousState = currentState;
            currentState = DOWN;
        } else if (mean < UP_THRESHOLD_SQUATS) {
            previousState = currentState;
            currentState = UP;
        }
    }

    private void checkIfSquated() {
        if (previousState == DOWN && currentState == UP) {
            squatCounter++;
            tvSquatCounter.setText("" + squatCounter);
            textToSpeech.speak(
                    String.valueOf(squatCounter), TextToSpeech.QUEUE_ADD, null
            );
        }
    }

    @Override
    public void onInit(int status) {

    }
}
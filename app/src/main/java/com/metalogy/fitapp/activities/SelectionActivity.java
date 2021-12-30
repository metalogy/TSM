package com.metalogy.fitapp.activities;

import static com.metalogy.fitapp.constants.Constants.POINTS_FOR_ACTIVITY_UNLOCK;
import static com.metalogy.fitapp.constants.Constants.POINTS_TEXT;
import static com.metalogy.fitapp.constants.Constants.PULLUP_TEXT;
import static com.metalogy.fitapp.constants.Constants.SHARED_PREFS_UNLOCKED;
import static com.metalogy.fitapp.constants.Constants.SHARED_PREFS_POINTS;
import static com.metalogy.fitapp.constants.Constants.STOPWATCH_TEXT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.metalogy.fitapp.R;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubView;

public class SelectionActivity extends AppCompatActivity {

    Button btnPushup;
    Button btnSquat;
    Button btnStopwatch;
    Button btnPullups;
    TextView tvPoints;

    private MoPubView moPubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        SdkConfiguration.Builder sdkConfiguration = new SdkConfiguration.Builder("b195f8dd8ded45fe847ad89ed1d016da"); //test
        MoPub.initializeSdk(this, sdkConfiguration.build(), initSdkListener());

        btnPushup = findViewById(R.id.btnPushup);
        btnSquat = findViewById(R.id.btnSquats);
        btnStopwatch = findViewById(R.id.btnStopwatch);
        btnPullups = findViewById(R.id.btnPullUps);
        tvPoints = findViewById(R.id.tvPoints);

        updatePointsCounter();
        updateLockedIcon(btnStopwatch, STOPWATCH_TEXT);

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

        btnStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectionActivity.this, VoiceStopwatchActivity.class));
                if (!getStatusOfLock(STOPWATCH_TEXT)) {
                    if (getPoints() >= POINTS_FOR_ACTIVITY_UNLOCK) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SelectionActivity.this);
                        builder.setTitle(R.string.app_name);
                        builder.setMessage("Do you want to buy activity for " + POINTS_FOR_ACTIVITY_UNLOCK + " points?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removePoints(POINTS_FOR_ACTIVITY_UNLOCK);
                                unlockActivity(STOPWATCH_TEXT);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    Toast.makeText(SelectionActivity.this, "Sorry, you need " + POINTS_FOR_ACTIVITY_UNLOCK + " to unlock activity!", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(SelectionActivity.this, VoiceStopwatchActivity.class));
                }
                updateLockedIcon(btnStopwatch, STOPWATCH_TEXT);
                updatePointsCounter();
            }
        });

        btnPullups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectionActivity.this, PullUpActivity.class));
                if (!getStatusOfLock(PULLUP_TEXT)) {
                    if (getPoints() >= POINTS_FOR_ACTIVITY_UNLOCK) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SelectionActivity.this);
                        builder.setTitle(R.string.app_name);
                        builder.setMessage("Do you want to buy activity for " + POINTS_FOR_ACTIVITY_UNLOCK + " points?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removePoints(POINTS_FOR_ACTIVITY_UNLOCK);
                                unlockActivity(PULLUP_TEXT);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    Toast.makeText(SelectionActivity.this, "Sorry, you need " + POINTS_FOR_ACTIVITY_UNLOCK + " to unlock activity!", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(SelectionActivity.this, VoiceStopwatchActivity.class));
                }
                updateLockedIcon(btnPullups, PULLUP_TEXT);
                updatePointsCounter();
            }
        });
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
           /* MoPub SDK initialized.
           Check if you should show the consent dialog here, and make your ad requests. */
                bannerAd();
            }
        };

    }

    private void bannerAd() {
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da"); // Enter your Ad Unit ID from www.mopub.com // test
        moPubView.loadAd();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void removePoints(int amount) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_POINTS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(POINTS_TEXT, getPoints() - amount);
        editor.apply();
    }

    public int getPoints() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_POINTS, MODE_PRIVATE);
        return sharedPreferences.getInt(POINTS_TEXT, 0);
    }

    public void updatePointsCounter() {
        tvPoints.setText("Points: " + getPoints());
    }

    public boolean getStatusOfLock(String activityName) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_UNLOCKED, MODE_PRIVATE);
        return sharedPreferences.getBoolean(activityName, false);
    }

    public void unlockActivity(String activityName) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_UNLOCKED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(activityName, true);
        editor.apply();
    }

    public void updateLockedIcon(Button button, String activityName) {
        if (getStatusOfLock(activityName)) {
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }
}
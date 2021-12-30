package com.metalogy.fitapp.activities;

import static com.metalogy.fitapp.constants.Constants.POINTS_FOR_WATCHING_AD;
import static com.metalogy.fitapp.constants.Constants.POINTS_TEXT;
import static com.metalogy.fitapp.constants.Constants.SHARED_PREFS_POINTS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.metalogy.fitapp.R;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

public class MainActivity extends AppCompatActivity implements MoPubInterstitial.InterstitialAdListener {

    Button btnExercise;
    Button btnAd;
    TextView tvPoints;

    private MoPubView moPubView;
    private MoPubInterstitial mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SdkConfiguration.Builder sdkConfiguration = new SdkConfiguration.Builder("b195f8dd8ded45fe847ad89ed1d016da"); //test
        MoPub.initializeSdk(this, sdkConfiguration.build(), initSdkListener());

        btnExercise = findViewById(R.id.btnExercise);
        btnAd = findViewById(R.id.btnAd);
        tvPoints = findViewById(R.id.tvPoints);

        updatePointsCounter();

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });

        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAd();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_POINTS, MODE_PRIVATE);
                addPoints(POINTS_FOR_WATCHING_AD);
                Toast.makeText(MainActivity.this, POINTS_FOR_WATCHING_AD + " added to your account!", Toast.LENGTH_SHORT).show();
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
                interstitialAd();
                bannerAd();
            }
        };
    }

    private void bannerAd() {
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da"); // Enter your Ad Unit ID from www.mopub.com // test
        moPubView.loadAd();
    }

    private void interstitialAd() {
        mInterstitial = new MoPubInterstitial(this, "24534e1901884e398f1253216226017e"); //test
        mInterstitial.setInterstitialAdListener(this);
        mInterstitial.load();
    }

    @Override
    protected void onDestroy() {
        mInterstitial.destroy();
        super.onDestroy();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit app!", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
        yourAppsShowInterstitialMethod();

    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {

    }

    private void yourAppsShowInterstitialMethod() {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        }
    }

    public void addPoints(int amount) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_POINTS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(POINTS_TEXT, getPoints() + amount);
        editor.apply();
    }

    public int getPoints() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_POINTS, MODE_PRIVATE);
        return sharedPreferences.getInt(POINTS_TEXT, 0);
    }

    public void updatePointsCounter() {
        tvPoints.setText("Points: " + getPoints());
    }
}
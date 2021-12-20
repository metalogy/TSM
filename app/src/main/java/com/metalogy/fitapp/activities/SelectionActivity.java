package com.metalogy.fitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.metalogy.fitapp.R;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubView;

public class SelectionActivity extends AppCompatActivity {

    Button btnPushup;
    Button btnSquat;
    Button btnStopwatch;

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
                startActivity(new Intent(SelectionActivity.this, VoiceStopwachActivity.class));
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
}
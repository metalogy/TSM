package com.metalogy.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubView;

public class MainActivity extends AppCompatActivity {

    Button btnExercise;
    Button btnWorkout;

    private MoPubView moPubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SdkConfiguration.Builder sdkConfiguration = new SdkConfiguration.Builder("b195f8dd8ded45fe847ad89ed1d016da"); //test
        MoPub.initializeSdk(this, sdkConfiguration.build(), initSdkListener());

        btnExercise = findViewById(R.id.btnExercise);
        btnWorkout = findViewById(R.id.btnWorkout);

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DUPA");
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
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

    private void bannerAd(){
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da"); // Enter your Ad Unit ID from www.mopub.com // test
        moPubView.loadAd();
    }

//    private void configureBtnExercise(){
//        btnExercise = findViewById(R.id.btnExercise);
//        btnExercise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
//            }
//        });
//    }


//    public void onClickEvent()
//    {
//        btnWorkout.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ExerciseSelection.class);
//                startActivity(intent);
//
//            }
//        });
//    }


//    public boolean onTouchEvent(MotionEvent event) {
//        int eventAction = event.getAction();
//        long time=0L;
//        long endTime=0L;
//        long pressTime=0L;
//
//
//
//        switch (eventAction) {
//            case MotionEvent.ACTION_DOWN:
//                time = SystemClock.elapsedRealtime();
//                Log.d("setOnTouchListener", "ACTION_DOWN at>>>" + time);
//                textView2.setText("DÓŁ");
//                break;
//            case MotionEvent.ACTION_UP:
//                endTime = SystemClock.elapsedRealtime();
//                textView2.setText("GÓRA");
//                Log.d("setOnTouchListener", "ACTION_UP at>>>" + endTime);
//                //pressTime= (Long)(endTime-time);
//                pressTime=Math.subtractExact(endTime,time);
//                Log.d("setOnTouchListener", "DIFF at>>>" + pressTime);
//                if (pressTime >= 2000) {
//                    curr = Integer.parseInt(textView.getText().toString());
//                    curr++;
//                    textView.setText(String.valueOf(curr));
//                }
//                break;
//        }
//        return true;
//    }

}
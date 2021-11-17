package com.metalogy.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnExercise;
    Button btnWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
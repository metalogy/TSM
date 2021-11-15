package com.metalogy.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.chuj);
        textView.setText("0");
    }

    public void clickListener(View v){
       curr=Integer.parseInt(textView.getText().toString());
       curr++;
       textView.setText(String.valueOf(curr));
    }


}
package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(() -> {
            //this should be signup activity honde
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }, time
        );
    }
}
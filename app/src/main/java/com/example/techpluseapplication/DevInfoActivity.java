package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DevInfoActivity extends AppCompatActivity {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dev_info);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(DevInfoActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });
    }
}
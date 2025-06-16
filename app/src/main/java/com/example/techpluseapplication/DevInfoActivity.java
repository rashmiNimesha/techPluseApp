package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DevInfoActivity extends AppCompatActivity {

    ImageButton backButton;
    Button releaseButton;
    TextView releaseInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dev_info);

        backButton = findViewById(R.id.backButton);
        releaseButton = findViewById(R.id.releaseButton);
        releaseInfoText = findViewById(R.id.releaseInfoText);

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(DevInfoActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        releaseButton.setOnClickListener(v -> {
            releaseInfoText.setVisibility(View.VISIBLE);
        });
    }
}
package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class UserInfoActivity extends AppCompatActivity {

    ImageButton back;
    Button editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        back = findViewById(R.id.backButton);
        editUser = findViewById(R.id.editUserButton);

        back.setOnClickListener(v -> {
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        editUser.setOnClickListener(v -> {
            Intent i = new Intent(this, EditUserActivity.class);
            startActivity(i);
            finish();
        });

    }
}
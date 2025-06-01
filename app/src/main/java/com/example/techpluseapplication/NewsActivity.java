package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {

    TextView newsTitle, newsDescription;
    ImageButton backButton;
    ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsTitle = findViewById(R.id.newsTitle);
        newsDescription = findViewById(R.id.newsDescription);
        newsImage = findViewById(R.id.newsImage);
        backButton = findViewById(R.id.backButtonNews);

        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String longDescription = getIntent().getStringExtra("longdescription");

        newsTitle.setText(title);
        newsDescription.setText(longDescription);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(newsImage);
        } else {
            newsImage.setVisibility(View.GONE);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
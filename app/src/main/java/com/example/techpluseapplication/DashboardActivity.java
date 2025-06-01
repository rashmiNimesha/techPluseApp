package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DashboardActivity extends AppCompatActivity {

    LinearLayout newsContainer;
    FirebaseFirestore db;
    BottomNavigationView bottomNav;
    ImageButton burgerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_dashboard);

        newsContainer = findViewById(R.id.newsContainer);
        bottomNav = findViewById(R.id.bottomNav);
        burgerMenu = findViewById(R.id.burgerMenu);
        View blurOverlay = findViewById(R.id.blurOverlay);

        db = FirebaseFirestore.getInstance();

        TextView dateText = findViewById(R.id.dateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, EEEE", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        dateText.setText(currentDate);

        fetchNewsByCategory("academics");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_academics) {
                fetchNewsByCategory("academics");
                return true;
            } else if (itemId == R.id.nav_sports) {
                fetchNewsByCategory("sports");
                return true;
            } else if (itemId == R.id.nav_events) {
                fetchNewsByCategory("events");
                return true;
            }

            return false;
        });


        burgerMenu.setOnClickListener(v -> {
            View popupView = getLayoutInflater().inflate(R.layout.activity_popup_menu, null);

            final PopupWindow popupWindow = new PopupWindow(popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);

            popupWindow.setElevation(20);
            popupWindow.setOutsideTouchable(true); // dismiss when touched outside
            popupWindow.setFocusable(true);

            blurOverlay.setVisibility(View.VISIBLE);

            popupWindow.showAsDropDown(v, -100, 20);

            popupWindow.setOnDismissListener(() -> blurOverlay.setVisibility(View.GONE));

            TextView devInfo = popupView.findViewById(R.id.devInfo);
            TextView userSettings = popupView.findViewById(R.id.userSettings);
            TextView logout = popupView.findViewById(R.id.logout);

            devInfo.setOnClickListener(view -> {
                popupWindow.dismiss();
                startActivity(new Intent(this, DevInfoActivity.class));
            });

            userSettings.setOnClickListener(view -> {
                popupWindow.dismiss();
                startActivity(new Intent(this, UserInfoActivity.class));
            });

            logout.setOnClickListener(view -> {
                popupWindow.dismiss();
                startActivity(new Intent(this, UserInfoActivity.class));
            });
        });


    }

    private void fetchNewsByCategory(String category) {
        newsContainer.removeAllViews();

        db.collection("news")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String title = doc.getString("title");
                            String description = doc.getString("content");
                            String imageUrl = doc.getString("imageUrl");
                            String longDescription = doc.getString("longdescription");

                            NewsItem news = new NewsItem(title, description, imageUrl, longDescription);

                            addNewsCard(news);
                        }

                        if (task.getResult().isEmpty()) {
                            Toast.makeText(this, "No news found in " + category, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to load news", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addNewsCard(NewsItem news) {
        View card = getLayoutInflater().inflate(R.layout.item_news_card, newsContainer, false);

        TextView title = card.findViewById(R.id.newsTitle);
        TextView desc = card.findViewById(R.id.newsDescription);
        ImageView image = card.findViewById(R.id.newsImage);
        Button readBtn = card.findViewById(R.id.readBtn);

        title.setText(news.getTitle());
        desc.setText(news.getDescription());

        if (news.getImageUrl() != null && !news.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(news.getImageUrl())
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }

        readBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, NewsActivity.class);
            intent.putExtra("title", news.getTitle());
            intent.putExtra("imageUrl", news.getImageUrl());
            intent.putExtra("longdescription", news.getLongDescription());
            startActivity(intent);
            finish();
        });

        newsContainer.addView(card);
    }


}
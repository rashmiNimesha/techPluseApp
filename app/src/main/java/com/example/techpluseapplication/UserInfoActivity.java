package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserInfoActivity extends AppCompatActivity {

    ImageButton back;
    Button editUser, logout , deleteButton;;
    TextView usernameText, emailText;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        back = findViewById(R.id.backButton);
        editUser = findViewById(R.id.editUserButton);
        logout = findViewById(R.id.logoutButton);
        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.email);
        deleteButton = findViewById(R.id.deleteButton);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

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

        logout.setOnClickListener(v -> showLogoutConfirmationDialog());

        if (currentUser != null) {
            String uid = currentUser.getUid();

            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String email = documentSnapshot.getString("email");
                            String username = documentSnapshot.getString("username");

                            emailText.setText("Email : " + email);
                            usernameText.setText("Username : " + username);
                        }
                    })
                    .addOnFailureListener(e -> {
                        usernameText.setText("Username : Error");
                        emailText.setText("Email : Error");
                    });
        }


        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        if (currentUser != null) {
            String uid = currentUser.getUid();
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String email = documentSnapshot.getString("email");
                            String username = documentSnapshot.getString("username");
                            emailText.setText("Email : " + email);
                            usernameText.setText("Username : " + username);
                        }
                    })
                    .addOnFailureListener(e -> {
                        usernameText.setText("Username : Error");
                        emailText.setText("Email : Error");
                    });
        }
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setTitle("Really Want to sign Out?");
        builder.setMessage("You will be logged out from your TechPulse account. You’ll need to log in again to access news and updates.");
        builder.setCancelable(true);

        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(dialog -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        });

        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        builder.setTitle("Delete Account?");
        builder.setMessage("This will permanently delete your account and you won't be able to log in again using this email.");
        builder.setCancelable(true);

        builder.setPositiveButton("Delete", (dialog, which) -> deleteUserAccount());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(dialog -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
        });

        alertDialog.show();
    }

    private void deleteUserAccount() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            db.collection("users").document(uid).delete()
                    .addOnSuccessListener(aVoid -> {
                        currentUser.delete()
                                .addOnSuccessListener(aVoid1 -> {
                                    Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    showErrorDialog("Account deletion failed. Please re-login and try again.");
                                });
                    })
                    .addOnFailureListener(e -> {
                        showErrorDialog("Error deleting user data. Try again.");
                    });
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(UserInfoActivity.this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }


}
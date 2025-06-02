package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText, currentPasswordEditText;
    private Button updateButton, cancelButton;
    private ImageButton backButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user);

        emailEditText = findViewById(R.id.emailtext);
        usernameEditText = findViewById(R.id.usernametext);
        passwordEditText = findViewById(R.id.passwordtext);
        currentPasswordEditText = findViewById(R.id.currentPassword);
        updateButton = findViewById(R.id.updateButton);
        cancelButton = findViewById(R.id.cancelButton);
        backButton = findViewById(R.id.backButton);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        updateButton.setOnClickListener(v -> updateUser());

        cancelButton.setOnClickListener(v -> {
            emailEditText.setText("");
            usernameEditText.setText("");
            passwordEditText.setText("");
            currentPasswordEditText.setText("");
            Toast.makeText(this, "Fields cleared.", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            Intent i = new Intent(this, UserInfoActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void updateUser() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        String newEmail = emailEditText.getText().toString().trim();
        String newUsername = usernameEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();
        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String currentEmail = currentUser.getEmail();

        if (currentPassword.isEmpty()) {
            Toast.makeText(this, "Enter current password to proceed.", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);

        currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> updates = new HashMap<>();

                if (!newEmail.isEmpty() && !newEmail.equals(currentEmail)) {
                    currentUser.updateEmail(newEmail).addOnSuccessListener(unused -> {
                        updates.put("email", newEmail);
                        updateFirestore(updates);
                    }).addOnFailureListener(e -> Toast.makeText(this, "Email update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    updates.put("email", currentEmail); // Keep current if not changed
                }

                if (!newPassword.isEmpty()) {
                    currentUser.updatePassword(newPassword).addOnSuccessListener(unused -> {
                        updates.put("password", newPassword);
                        updateFirestore(updates);
                    }).addOnFailureListener(e -> Toast.makeText(this, "Password update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }

                if (!newUsername.isEmpty()) {
                    updates.put("username", newUsername);
                    updateFirestore(updates);
                }

                if (updates.isEmpty()) {
                    Toast.makeText(this, "No changes made.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Reauthentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFirestore(Map<String, Object> updates) {
        String uid = currentUser.getUid();
        firestore.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "User info updated.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Firestore update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}

package com.example.techpluseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, username, password, confirmPassword;
    private Button signUpBtn;
    private CheckBox checkboxConsent;
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpBtn = findViewById(R.id.signupBtn);
        checkboxConsent = findViewById(R.id.checkboxConsent);

        signUpBtn.setOnClickListener(v -> registerUser());

    }

    private void registerUser(){
        String emailInput = email.getText().toString().trim();
        String usernameInput = username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();

        if (emailInput.isEmpty() || usernameInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordInput.equals(confirmPasswordInput)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkboxConsent.isChecked()) {
            Toast.makeText(this, "You must agree to the terms", Toast.LENGTH_SHORT).show();
            return;
        }

//        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        String userId = mAuth.getCurrentUser().getUid();
//                        // Save additional user data
//                        HashMap<String, String> userMap = new HashMap<>();
//                        userMap.put("email", emailInput);
//                        userMap.put("username", usernameInput);
//
//                        mDatabase.child(userId).setValue(userMap).addOnCompleteListener(task1 -> {
//                            if (task1.isSuccessful()) {
//                                Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//                                finish();
//                            } else {
//                                Toast.makeText(SignUpActivity.this, "Database error", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    } else {
//                        Toast.makeText(SignUpActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}
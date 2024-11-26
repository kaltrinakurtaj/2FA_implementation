package com.example.a2fa_impl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private static final String VALID_EMAIL = "usern@email.com";
    private static final String VALID_PASSWORD = "yourpassword";
    private String generatedOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD)) {
                // Generate OTP
                generatedOTP = generateOTP();

                // Send OTP via email
                sendOtpToEmail(email, generatedOTP);

                // Redirect to VerifyOtpActivity
                Intent intent = new Intent(LoginActivity.this, VerifyOtpActivity.class);
                intent.putExtra("generatedOTP", generatedOTP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to generate a 6-digit OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Function to send OTP via email
    private void sendOtpToEmail(String recipient, String otp) {
        new Thread(() -> {
            try {
                JavaMailSender mailSender = new JavaMailSender("user@email.com", "yourpassword");
                mailSender.sendOtpEmail(recipient, otp);
                runOnUiThread(() -> Toast.makeText(this, "OTP has been sent to your email", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to send OTP. Try again.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}

package com.example.a2fa_impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2fa_impl.JavaMailSender;

import java.util.concurrent.Executors;

public class VerifyOtpActivity extends AppCompatActivity {

    private String generatedOTP;
    private EditText otpInput;
    private Button verifyOTPButton, resendOTPButton;
    private TextView countdownText;
    private String recipientEmail = "user@email.com"; // Replace with dynamic email if needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        // Get the generated OTP from intent
        generatedOTP = getIntent().getStringExtra("generatedOTP");

        // Initialize UI components
        otpInput = findViewById(R.id.otpInput);
        verifyOTPButton = findViewById(R.id.verifyOTPButton);
        resendOTPButton = findViewById(R.id.resendOTPButton);
        countdownText = findViewById(R.id.countdownText);

        // Start countdown for resend button
        startCountdown();

        // Verify OTP button click listener
        verifyOTPButton.setOnClickListener(v -> {
            String enteredOTP = otpInput.getText().toString();

            if (enteredOTP.equals(generatedOTP)) {
                Toast.makeText(this, "OTP Verified! Login Successful.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VerifyOtpActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid OTP! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        // Resend OTP button click listener
        resendOTPButton.setOnClickListener(v -> {
            resendOTP();
        });
    }

    private void startCountdown() {
        resendOTPButton.setEnabled(false);
        countdownText.setText("You can resend OTP in 30 seconds");

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownText.setText("You can resend OTP in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                resendOTPButton.setEnabled(true);
                countdownText.setText("You can now resend the OTP.");
            }
        }.start();
    }

    private void resendOTP() {
        // Generate new OTP
        generatedOTP = String.valueOf((int) (Math.random() * 900000) + 100000); // New 6-digit OTP

        // Send the new OTP using JavaMailSender
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                JavaMailSender mailSender = new JavaMailSender("username@email.com", "yourpassword"); // Replace with your credentials
                mailSender.sendOtpEmail(recipientEmail, generatedOTP);
                runOnUiThread(() -> Toast.makeText(this, "OTP Resent Successfully!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to resend OTP: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        // Restart the countdown
        startCountdown();
    }
}

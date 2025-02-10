package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BankDetailsActivity extends AppCompatActivity {

    private EditText editTextAccountNumber, editTextExpirationDate, editTextCVV, editTextEmail;
    private Button buttonSubmitBankDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextExpirationDate = findViewById(R.id.editTextExpirationDate);
        editTextCVV = findViewById(R.id.editTextCVV);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSubmitBankDetails = findViewById(R.id.buttonSubmitBankDetails);

        buttonSubmitBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void validateAndSubmit() {
        String accountNumber = editTextAccountNumber.getText().toString().trim();
        String expirationDate = editTextExpirationDate.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        // Basic validation
        if (accountNumber.length() != 16) {
            showErrorDialog("Error", "Account number must be 16 digits!");
            return;
        }

        if (!expirationDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            showErrorDialog("Error", "Enter a valid expiration date (MM/YY)!");
            return;
        }

        if (cvv.length() != 3) {
            showErrorDialog("Error", "CVV must be 3 digits!");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showErrorDialog("Error", "Enter a valid email address!");
            return;
        }

        // Get current date
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Show success dialog
        showSuccessDialog(email, currentDate);
    }

    private void showSuccessDialog(String email, String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Confirmation");
        builder.setMessage("✅ Sent to supplier!\n📧 Receipt has been emailed to: " + email + "\n📅 Date: " + date);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect to BasketActivity after clicking OK
                Intent intent = new Intent(BankDetailsActivity.this, BasketActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Close this activity
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

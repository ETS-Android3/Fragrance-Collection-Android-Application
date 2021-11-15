package com.example.fragrancecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmPasswordActivity extends AppCompatActivity {

    private EditText currentPasswordEditText;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        currentPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        continueButton = findViewById(R.id.confirmPasswordContinueButton);


        String currentPassword = getIntent().getExtras().getString("password");

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentPasswordEditText.getText().toString().equals(currentPassword) || currentPasswordEditText.getText().toString().isEmpty()) {
                    currentPasswordEditText.setError("Invalid password");
                    return;
                }

                Intent toEditUserIntent = new Intent(ConfirmPasswordActivity.this, EditUserActivity.class);
                toEditUserIntent.putExtras(getIntent().getExtras());
                startActivity(toEditUserIntent);
            }
        });

    }
}
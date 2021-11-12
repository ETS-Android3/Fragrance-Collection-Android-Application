package com.example.fragrancecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private ScrollView loginScrollView;
    private EditText loginEmailEditText;
    private EditText loginPasswordEditText;
    private Button loginButton;
    private TextView redirectToRegisterTextView;

    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginScrollView = findViewById(R.id.registerScrollView);
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginButton = findViewById(R.id.loginButton);
        redirectToRegisterTextView = findViewById(R.id.redirectToRegisterTextView);

        databaseHelper = new DatabaseHelper(LoginActivity.this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = loginEmailEditText.getText().toString().trim();
                String passwordValue = loginPasswordEditText.getText().toString().trim();

                if (emailValue.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches())
                    loginEmailEditText.setError("Enter a valid email");

                if (passwordValue.isEmpty())
                    loginPasswordEditText.setError("Enter a password");

                if (databaseHelper.checkUserExists(emailValue, passwordValue))
                    startActivity(new Intent(LoginActivity.this, FragranceListActivity.class));

                else
                    Snackbar.make(loginScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        });

        redirectToRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
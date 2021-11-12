package com.example.fragrancecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private ScrollView registerScrollView;
    private EditText registerNameEditText;
    private EditText registerEmailEditText;
    private EditText registerPasswordEditText;
    private EditText registerConfirmPasswordEditText;
    private Button registerButton;
    private TextView redirectToLoginTextView;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerScrollView = findViewById(R.id.registerScrollView);
        registerNameEditText = findViewById(R.id.registerNameEditText);
        registerEmailEditText = findViewById(R.id.registerEmailEditText);
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText);
        registerConfirmPasswordEditText = findViewById(R.id.registerConfirmEditText);
        registerButton = findViewById(R.id.registerButton);
        redirectToLoginTextView = findViewById(R.id.redirectToLoginTextView);

        databaseHelper = new DatabaseHelper(RegisterActivity.this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = registerNameEditText.getText().toString().trim();
                String emailValue = registerEmailEditText.getText().toString().trim();
                String passwordValue = registerPasswordEditText.getText().toString().trim();
                String confirmPasswordValue = registerConfirmPasswordEditText.getText().toString().trim();
                boolean formIsValid = true;

                if (nameValue.isEmpty()) {
                    registerNameEditText.setError("Enter a valid name");
                    formIsValid = false;
                }

                if (emailValue.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
                    registerEmailEditText.setError("Enter a valid email");
                    formIsValid = false;
                }

                if (passwordValue.isEmpty()) {
                    registerPasswordEditText.setError("Enter a password");
                    formIsValid = false;
                }

                if (!passwordValue.equals(confirmPasswordValue)) {
                    registerConfirmPasswordEditText.setError("Passwords do not match");
                    formIsValid = false;
                }

                if (!formIsValid)
                    return;


                if (!databaseHelper.checkUserExists(emailValue)) {
                    user = new User(registerNameEditText.getText().toString(), registerEmailEditText.getText().toString(),
                            registerPasswordEditText.getText().toString());

                    databaseHelper.addUser(user);
                    clearRegistrationForm();
                    Snackbar.make(registerScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                }
                else
                    Snackbar.make(registerScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void clearRegistrationForm(){
        registerNameEditText.setText(null);
        registerEmailEditText.setText(null);
        registerPasswordEditText.setText(null);
        registerConfirmPasswordEditText.setText(null);
    }
}
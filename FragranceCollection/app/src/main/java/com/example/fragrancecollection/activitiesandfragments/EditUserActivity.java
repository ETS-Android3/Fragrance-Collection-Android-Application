package com.example.fragrancecollection.activitiesandfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.fragrancecollection.DatabaseHelper;
import com.example.fragrancecollection.R;
import com.example.fragrancecollection.models.User;
import com.google.android.material.snackbar.Snackbar;

public class EditUserActivity extends AppCompatActivity {

    private ScrollView editUserScrollView;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button editUserButton;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editUserScrollView = findViewById(R.id.editUserScrollView);
        nameEditText = findViewById(R.id.editUserNameEditText);
        emailEditText = findViewById(R.id.editUserEmailEditText);
        passwordEditText = findViewById(R.id.editUserPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.editUserConfirmPasswordEditText);
        editUserButton = findViewById(R.id.editUserButton);

        int userId = getIntent().getExtras().getInt("userId");
        String currentName = getIntent().getExtras().getString("name");
        String currentEmail = getIntent().getExtras().getString("email");
        String currentPassword = getIntent().getExtras().getString("password");

        nameEditText.setText(currentName);
        emailEditText.setText(currentEmail);
        passwordEditText.setText(currentPassword);
        confirmPasswordEditText.setText(currentPassword);

        databaseHelper = new DatabaseHelper(EditUserActivity.this);

        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = nameEditText.getText().toString().trim();
                String emailValue = emailEditText.getText().toString().trim();
                String passwordValue = passwordEditText.getText().toString().trim();
                String confirmPasswordValue = confirmPasswordEditText.getText().toString().trim();
                boolean formIsValid = true;

                if (nameValue.isEmpty()) {
                    nameEditText.setError("Enter a valid name");
                    formIsValid = false;
                }

                if (emailValue.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
                    emailEditText.setError("Enter a valid email");
                    formIsValid = false;
                }

                if (passwordValue.isEmpty()) {
                    passwordEditText.setError("Enter a password");
                    formIsValid = false;
                }

                if (!passwordValue.equals(confirmPasswordValue)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    formIsValid = false;
                }

                if (!formIsValid)
                    return;

                if (!databaseHelper.checkUserExists(emailValue) || emailValue.equals(currentEmail)) {
                    user = new User(userId, nameValue, emailValue, passwordValue);
                    databaseHelper.updateUser(user);
                    Intent toMainIntent = new Intent(EditUserActivity.this, MainActivity.class);
                    toMainIntent.putExtra("email", emailValue);
                    toMainIntent.putExtra("password", passwordValue);
                    toMainIntent.putExtra("source", "editUserActivity");
                    startActivity(toMainIntent);
                }
                else
                    Snackbar.make(editUserScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
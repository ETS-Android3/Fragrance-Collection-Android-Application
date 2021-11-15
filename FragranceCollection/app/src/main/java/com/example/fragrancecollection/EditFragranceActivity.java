package com.example.fragrancecollection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditFragranceActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText perfumerEditText;
    private EditText releaseYearEditText;
    private EditText notesEditText;
    private ImageButton chooseFragranceImageButton;
    private Button editFragranceButton;
    private ImageView chosenImageView;

    private Bitmap bitmap;
    private Fragrance fragrance;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fragrance);

        nameEditText = findViewById(R.id.editFragranceNameEditText);
        perfumerEditText = findViewById(R.id.editFragrancePerfumerEditText);
        releaseYearEditText = findViewById(R.id.editFragranceReleaseYearEditText);
        notesEditText = findViewById(R.id.editFragranceNotesEditText);
        chooseFragranceImageButton = findViewById(R.id.editFragranceChooseImageButton);
        editFragranceButton = findViewById(R.id.editFragranceButton);
        chosenImageView = findViewById(R.id.editFragranceChosenImageView);

        int receivedFragranceId = getIntent().getExtras().getInt("fragranceId");
        int receivedUserId = getIntent().getExtras().getInt("userId");
        String receivedName = getIntent().getExtras().getString("name");
        String receivedPerfumer = getIntent().getExtras().getString("perfumer");
        int receivedReleaseYear = getIntent().getExtras().getInt("releaseYear");
        String receivedNotes = getIntent().getExtras().getString("notes");
        byte[] receivedImage = getIntent().getExtras().getByteArray("image");
        String currentLoggedUserEmail = getIntent().getExtras().getString("email");
        String currentLoggedUserPassword = getIntent().getExtras().getString("password");
        bitmap = BitmapFactory.decodeByteArray(receivedImage, 0, receivedImage.length);

        nameEditText.setText(receivedName);
        perfumerEditText.setText(receivedPerfumer);
        releaseYearEditText.setText(String.valueOf(receivedReleaseYear));
        notesEditText.setText(receivedNotes);
        chosenImageView.setImageBitmap(bitmap);
        chosenImageView.setAlpha(1f);

        databaseHelper = new DatabaseHelper(EditFragranceActivity.this);

        chooseFragranceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, 3);
            }
        });

        editFragranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = nameEditText.getText().toString().trim();
                String perfumerValue = perfumerEditText.getText().toString().trim();
                int releaseYearValue = 0;
                if (!releaseYearEditText.getText().toString().isEmpty()) {
                    releaseYearValue = Integer.parseInt(releaseYearEditText.getText().toString().trim());
                }
                String notesValue = notesEditText.getText().toString().trim();
                boolean formIsValid = true;

                if (nameValue.isEmpty()) {
                    nameEditText.setError("Enter a valid name");
                    formIsValid = false;
                }

                if (perfumerValue.isEmpty()) {
                    perfumerEditText.setError("Enter a perfumer");
                    formIsValid = false;
                }

                if (releaseYearEditText.getText().toString().isEmpty()) {
                    releaseYearEditText.setError("Enter a release year");
                    formIsValid = false;
                }

                if (notesValue.isEmpty()) {
                    notesEditText.setError("Enter notes");
                    formIsValid = false;
                }


                if (!formIsValid)
                    return;


                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    fragrance = new Fragrance(receivedFragranceId, receivedUserId, nameValue, perfumerValue, releaseYearValue, notesValue, stream.toByteArray());
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                databaseHelper.updateFragrance(fragrance);
                Intent toMainIntent = new Intent(EditFragranceActivity.this, MainActivity.class);
                toMainIntent.putExtra("email", currentLoggedUserEmail);
                toMainIntent.putExtra("password", currentLoggedUserPassword);
                toMainIntent.putExtra("source", "editFragranceActivity");
                EditFragranceActivity.this.startActivity(toMainIntent);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null){
            Uri selectedImage = data.getData();
            chosenImageView = findViewById(R.id.editFragranceChosenImageView);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(EditFragranceActivity.this.getContentResolver(), selectedImage);
                chosenImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(EditFragranceActivity.this, "Unexpected Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
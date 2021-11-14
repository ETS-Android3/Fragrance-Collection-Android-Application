package com.example.fragrancecollection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddFragranceFragment extends Fragment {

    private EditText nameEditText;
    private EditText perfumerEditText;
    private EditText releaseYearEditText;
    private EditText notesEditText;
    private ImageButton chooseFragranceImageButton;
    private Button addFragranceButton;
    private ImageView chosenImageView;

    private Bitmap bitmap;
    private Fragrance fragrance;
    private DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_fragrance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = getView().findViewById(R.id.addFragranceNameEditText);
        perfumerEditText = getView().findViewById(R.id.addFragrancePerfumerEditText);
        releaseYearEditText = getView().findViewById(R.id.addFragranceReleaseYearEditText);
        notesEditText = getView().findViewById(R.id.addFragranceNotesEditText);
        addFragranceButton = getView().findViewById(R.id.addFragranceButton);
        chooseFragranceImageButton = getView().findViewById(R.id.addFragranceChooseImageButton);

        String currentLoggedUserEmail = getActivity().getIntent().getExtras().getString("email");
        String currentLoggedUserPassword = getActivity().getIntent().getExtras().getString("password");

        databaseHelper = new DatabaseHelper(getActivity());

        chooseFragranceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, 3);
            }
        });

        addFragranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = nameEditText.getText().toString().trim();
                String perfumerValue = perfumerEditText.getText().toString().trim();
                int releaseYearValue = Integer.parseInt(releaseYearEditText.getText().toString().trim());
                String notesValue = notesEditText.getText().toString().trim();
                int currentLoggedUserId = databaseHelper.checkUserExists(currentLoggedUserEmail, currentLoggedUserPassword);

                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    fragrance = new Fragrance(currentLoggedUserId, nameValue, perfumerValue, releaseYearValue, notesValue, stream.toByteArray());
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                databaseHelper.addFragrance(fragrance);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null){
            Uri selectedImage = data.getData();
            chosenImageView = getView().findViewById(R.id.addFragranceChosenImageView);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                chosenImageView.setImageBitmap(bitmap);
                chosenImageView.setAlpha(1f);
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Unexpected Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

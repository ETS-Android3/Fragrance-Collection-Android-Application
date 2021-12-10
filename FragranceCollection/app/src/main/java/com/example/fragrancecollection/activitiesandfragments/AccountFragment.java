package com.example.fragrancecollection.activitiesandfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragrancecollection.DatabaseHelper;
import com.example.fragrancecollection.R;
import com.example.fragrancecollection.models.Fragrance;
import com.example.fragrancecollection.models.User;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView fragranceCountTextView;
    private Button editAccountButton;
    private Button logoutButton;

    private DatabaseHelper databaseHelper;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = getView().findViewById(R.id.accountFragmentNameTextView);
        emailTextView = getView().findViewById(R.id.accountFragmentEmailTextView);
        fragranceCountTextView = getView().findViewById(R.id.accountFragmentFragranceCountTextView);
        editAccountButton = getView().findViewById(R.id.accountFragmentEditButton);
        logoutButton = getView().findViewById(R.id.accountFragmentLogoutButton);

        databaseHelper = new DatabaseHelper(getActivity());
        user = databaseHelper.getUser(MainActivity.currentLoggedUserEmail);

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());


        ArrayList<Fragrance> currentUserFragrances = databaseHelper.getAllFragrances(user.getUserId());
        fragranceCountTextView.setText(String.valueOf(currentUserFragrances.size()));

        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConfirmPasswordIntent = new Intent(getActivity(), ConfirmPasswordActivity.class);
                toConfirmPasswordIntent.putExtra("userId", user.getUserId());
                toConfirmPasswordIntent.putExtra("name", user.getName());
                toConfirmPasswordIntent.putExtra("email", user.getEmail());
                toConfirmPasswordIntent.putExtra("password", user.getPassword());
                startActivity(toConfirmPasswordIntent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.context, R.style.AlertDialogCustom);
                builder.setIcon(R.drawable.ic_baseline_account_circle_24)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }
}

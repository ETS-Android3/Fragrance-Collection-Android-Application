package com.example.fragrancecollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class VisitProfileActivity extends AppCompatActivity {

    private TextView titleTextView;
    private RecyclerView visitProfileRecyclerView;
    private VisitProfileAdapterClass adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profile);

        titleTextView = findViewById(R.id.visitProfileTitleTextView);

        visitProfileRecyclerView = findViewById(R.id.visitProfileRecyclerView);
        databaseHelper = new DatabaseHelper(VisitProfileActivity.this);

        int userId = getIntent().getExtras().getInt("userId");
        String userName = getIntent().getExtras().getString("name");

        if (userName.contains(" ")) {
            userName = userName.split(" ")[0];
        }
        titleTextView.setText(userName + "'s Collection");

        ArrayList<Fragrance> fragrances = databaseHelper.getAllFragrances(userId);

        adapter = new VisitProfileAdapterClass(fragrances);
        visitProfileRecyclerView.setLayoutManager(new LinearLayoutManager(VisitProfileActivity.this));
        visitProfileRecyclerView.setAdapter(adapter);
    }
}
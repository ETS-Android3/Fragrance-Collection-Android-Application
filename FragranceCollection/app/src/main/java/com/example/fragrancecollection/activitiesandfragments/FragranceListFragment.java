package com.example.fragrancecollection.activitiesandfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragrancecollection.DatabaseHelper;
import com.example.fragrancecollection.R;
import com.example.fragrancecollection.adapters.AdapterClass;
import com.example.fragrancecollection.models.Fragrance;

import java.util.ArrayList;

public class FragranceListFragment extends Fragment {

    private RecyclerView fragranceListRecyclerView;
    private AdapterClass adapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragrance_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragranceListRecyclerView = getView().findViewById(R.id.fragranceListRecyclerView);
        String currentLoggedUserEmail = null;
        String currentLoggedUserPassword = null;

        try {
            currentLoggedUserEmail = getActivity().getIntent().getExtras().getString("email");
            currentLoggedUserPassword = getActivity().getIntent().getExtras().getString("password");
        }catch (Exception e) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }


        databaseHelper = new DatabaseHelper(getActivity());

        int currentLoggedUserId = databaseHelper.checkUserExists(currentLoggedUserEmail, currentLoggedUserPassword);
        ArrayList<Fragrance> fragrances = databaseHelper.getAllFragrances(currentLoggedUserId);

        adapter = new AdapterClass(fragrances);
        fragranceListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragranceListRecyclerView.setAdapter(adapter);

    }
}

package com.example.fragrancecollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    private RecyclerView userListRecyclerView;
    private UserAdapterClass adapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userListRecyclerView = getView().findViewById(R.id.userListRecyclerView);
        databaseHelper = new DatabaseHelper(getActivity());

        String currentLoggedUserEmail = getActivity().getIntent().getExtras().getString("email");
        String currentLoggedUserPassword = getActivity().getIntent().getExtras().getString("password");

        int currentLoggedUserId = databaseHelper.checkUserExists(currentLoggedUserEmail, currentLoggedUserPassword);
        ArrayList<User> users = databaseHelper.getAllUsers(currentLoggedUserId);

        adapter = new UserAdapterClass(users);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userListRecyclerView.setAdapter(adapter);

    }
}

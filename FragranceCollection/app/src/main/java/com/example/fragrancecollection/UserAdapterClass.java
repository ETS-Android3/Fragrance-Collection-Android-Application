package com.example.fragrancecollection;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapterClass extends RecyclerView.Adapter<UserAdapterClass.ViewHolder> {

    private ArrayList<User> users;

    public UserAdapterClass(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_list_row, parent, false);
        UserAdapterClass.ViewHolder viewHolder = new UserAdapterClass.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterClass.ViewHolder holder, int position) {
        User user = users.get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.context);
        ArrayList<Fragrance> fragrances = databaseHelper.getAllFragrances(user.getUserId());

        holder.nameTextView.setText(user.getName());
        holder.fragrancesTextView.setText(String.valueOf(fragrances.size() + " Fragrances In Collection"));

        holder.visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toVisitProfileIntent = new Intent(MainActivity.context, VisitProfileActivity.class);
                toVisitProfileIntent.putExtra("userId", user.getUserId());
                toVisitProfileIntent.putExtra("name", user.getName());
                MainActivity.context.startActivity(toVisitProfileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView fragrancesTextView;
        public Button visitButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.userListNameTextView);
            fragrancesTextView = itemView.findViewById(R.id.userListFragrancesTextView);
            visitButton = itemView.findViewById(R.id.userListVisitButton);
        }
    }
}

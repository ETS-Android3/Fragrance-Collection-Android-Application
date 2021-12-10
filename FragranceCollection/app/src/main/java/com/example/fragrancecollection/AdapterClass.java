package com.example.fragrancecollection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{

    private ArrayList<Fragrance> fragrances;

    public AdapterClass(ArrayList<Fragrance> fragrances) {
        this.fragrances = fragrances;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragrance_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fragrance fragrance = fragrances.get(position);
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.context);

        int likesCount = databaseHelper.getLikeCount(fragrance.getFragranceId());

        byte[] image = fragrance.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.nameTextView.setText(fragrance.getName());
        holder.perfumerTextView.setText(fragrance.getPerfumer());
        holder.releaseYearTextView.setText(String.valueOf(fragrance.getReleaseYear()));
        holder.notesTextView.setText(fragrance.getNotes());
        holder.likesTextView.setText("Likes: " + likesCount);
        holder.imageView.setImageBitmap(bitmap);
        holder.editButton.setText("Edit");
        holder.deleteButton.setText("Delete");

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.context, R.style.AlertDialogCustom);
                builder.setIcon(R.drawable.ic_baseline_delete_24)
                        .setTitle("Delete Fragrance")
                        .setMessage("Are you sure you want to delete " + fragrance.getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHelper.deleteFragrance(fragrance);
                                fragrances.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEditIntent = new Intent(MainActivity.context, EditFragranceActivity.class);
                toEditIntent.putExtra("fragranceId", fragrance.getFragranceId());
                toEditIntent.putExtra("userId", fragrance.getUserId());
                toEditIntent.putExtra("name", fragrance.getName());
                toEditIntent.putExtra("perfumer", fragrance.getPerfumer());
                toEditIntent.putExtra("releaseYear", fragrance.getReleaseYear());
                toEditIntent.putExtra("notes", fragrance.getNotes());
                toEditIntent.putExtra("image", fragrance.getImage());
                toEditIntent.putExtra("email", MainActivity.currentLoggedUserEmail);
                toEditIntent.putExtra("password", MainActivity.currentLoggedUserPassword);

                MainActivity.context.startActivity(toEditIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return fragrances.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView perfumerTextView;
        public TextView releaseYearTextView;
        public TextView notesTextView;
        public TextView likesTextView;
        public ImageView imageView;
        public Button editButton;
        public Button deleteButton;
        public RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.fragranceListNameTextView);
            perfumerTextView = itemView.findViewById(R.id.fragranceListPerfumerTextView);
            releaseYearTextView = itemView.findViewById(R.id.fragranceListReleaseYearTextView);
            notesTextView = itemView.findViewById(R.id.fragranceListNotesTextView);
            likesTextView = itemView.findViewById(R.id.fragranceListLikesTextView);
            imageView = itemView.findViewById(R.id.fragranceListImageView);
            editButton = itemView.findViewById(R.id.fragranceListEditButton);
            deleteButton = itemView.findViewById(R.id.fragranceListDeleteButton);
            recyclerView = itemView.findViewById(R.id.fragranceListRecyclerView);
        }
    }
}

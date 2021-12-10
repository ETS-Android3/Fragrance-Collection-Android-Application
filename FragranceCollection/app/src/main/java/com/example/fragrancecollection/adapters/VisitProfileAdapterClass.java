package com.example.fragrancecollection.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragrancecollection.DatabaseHelper;
import com.example.fragrancecollection.models.Fragrance;
import com.example.fragrancecollection.activitiesandfragments.MainActivity;
import com.example.fragrancecollection.R;

import java.util.ArrayList;

public class VisitProfileAdapterClass extends RecyclerView.Adapter<VisitProfileAdapterClass.ViewHolder> {

    public ArrayList<Fragrance> fragrances;

    public VisitProfileAdapterClass(ArrayList<Fragrance> fragrances) {
        this.fragrances = fragrances;
    }

    @NonNull
    @Override
    public VisitProfileAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.visit_profile_row, parent, false);
        VisitProfileAdapterClass.ViewHolder viewHolder = new VisitProfileAdapterClass.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VisitProfileAdapterClass.ViewHolder holder, int position) {
        Fragrance fragrance = fragrances.get(position);
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.context);
        int likesCount = databaseHelper.getLikeCount(fragrance.getFragranceId());

        int currentLoggedUserId = databaseHelper.checkUserExists(MainActivity.currentLoggedUserEmail,
                MainActivity.currentLoggedUserPassword);

        final boolean[] isLiked = {databaseHelper.checkIfLikedAlready(currentLoggedUserId, fragrance.getFragranceId())};

        if (isLiked[0]) {
            holder.likeButton.setText("Unlike");
            holder.likeButton.setBackgroundColor(Color.parseColor("#BF3757"));
        }

        else {
            holder.likeButton.setText("Like");
            holder.likeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
        }

        byte[] image = fragrance.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.nameTextView.setText(fragrance.getName());
        holder.perfumerTextView.setText(fragrance.getPerfumer());
        holder.releaseYearTextView.setText(String.valueOf(fragrance.getReleaseYear()));
        holder.notesTextView.setText(fragrance.getNotes());
        holder.likesTextView.setText("Likes: " + likesCount);
        holder.imageView.setImageBitmap(bitmap);

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLiked[0]) {
                    databaseHelper.deleteLike(currentLoggedUserId, fragrance.getFragranceId());
                    holder.likeButton.setText("Like");
                    holder.likeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
                }

                else {
                    databaseHelper.addLike(currentLoggedUserId, fragrance.getFragranceId());
                    holder.likeButton.setText("Unlike");
                    holder.likeButton.setBackgroundColor(Color.parseColor("#BF3757"));
                }

                isLiked[0] = !isLiked[0];
                notifyDataSetChanged();
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
        public Button likeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.visitProfileNameTextView);
            perfumerTextView = itemView.findViewById(R.id.visitProfilePerfumerTextView);
            releaseYearTextView = itemView.findViewById(R.id.visitProfileReleaseYearTextView);
            notesTextView = itemView.findViewById(R.id.visitProfileNotesTextView);
            likesTextView = itemView.findViewById(R.id.visitProfileLikesTextView);
            imageView = itemView.findViewById(R.id.visitProfileImageView);
            likeButton = itemView.findViewById(R.id.visitProfileLikeButton);

        }
    }
}

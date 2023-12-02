package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoHolder> implements ListClickListener<PhotoHolder> {
    private final Context context;
    private final List<Photo> photos;
    private boolean isEditingState = false;
    private final VisibilityCallback mVisibilityCallback;

    public PhotoListAdapter(Context context, List<Photo> photos, VisibilityCallback visibilityCallback) {
        this.context = context;
        this.photos = photos;
        this.mVisibilityCallback = visibilityCallback;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_grid, parent, false);
        return new PhotoHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        Photo photo = photos.get(position);
        Picasso picasso = new Picasso.Builder(context)
                .listener((picasso1, uri, exception) -> {
                    Log.i("Picasso", "uri: " + uri);
                    Log.i("Picasso", "Exception: " + exception);
                })
                .build();

        if (photo != null && !photo.getUrl().equals("")) {
            Log.i("Ryan", "Attempting to get photo: " + photo.getUrl());
            Picasso.get()
                    .load(photo.getUrl())
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_launcher_foreground))//it will show placeholder image when url is not valid.
                    .into(holder.img_android);
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public void onItemClick(int position, PhotoHolder holder) {
        // behaviour if following a long click event.
        Log.i("Ryan", "single click outside");
        if (isEditingState) {
            Log.i("Ryan", "single click inside");
            Photo photo = photos.get(position);
            photo.toggleSelected();
            notifyDataSetChanged();
            return;
        }

    }

    @Override
    public void onItemLongClick(int position, PhotoHolder holder) {
        isEditingState = true;

        if (mVisibilityCallback != null) {
            Log.i("Ryan", "long click inside");
            Photo photo = photos.get(position);
            photo.setSelected(true);
            mVisibilityCallback.toggleVisibility();
            notifyDataSetChanged();
        }
    }

}

package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Photo;
import com.squareup.picasso.Picasso;

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

        // sets checked condition for photo
        holder.setChecked(photo.getSelected());

        // shows checkable view holder if in editing state
        holder.revealChecked(isEditingState);

        if (photo != null && !photo.getUrl().equals("")) {
            Log.i("CRUD", "Attempting to get photo: " + photo.getUrl());
            Picasso.get()
                    .load(photo.getUrl())
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)) //it will show placeholder image when url is not valid/or when loading
                    .resize(500,500)
                    .centerCrop()
                    .into(holder.img_android);
        }
    }

    /**
     *
     * @return the total count of photos
     */
    @Override
    public int getItemCount() {
        return photos.size();
    }

    /**
     * Handles single click behaviour during photo selection for deletion
     * @param position the position in the adapter
     * @param holder the view holder (PhotoHolder)
     */
    @Override
    public void onItemClick(int position, PhotoHolder holder) {
        // behaviour if following a long click event.
        if (isEditingState) {
            Photo photo = photos.get(position);
            photo.toggleSelected();
            notifyDataSetChanged();
        }

    }

    /**
     * Handles the long click for the photo list
     * @param position location within the adapter
     * @param holder the PhotoHolder
     */
    @Override
    public void onItemLongClick(int position, PhotoHolder holder) {
        // cancel editing state
        if(isEditingState) {
            mVisibilityCallback.toggleVisibility();
            isEditingState = false;
            notifyDataSetChanged();
            return;
        }

        isEditingState = true;

        if (mVisibilityCallback != null) {
            Photo photo = photos.get(position);
            photo.setSelected(true);
            // show the trash can
            mVisibilityCallback.toggleVisibility();
            notifyDataSetChanged();
        }
    }

    /**
     * Sets the state condition for deleting photos
     * @param state declares if the editing state is active or not
     */
    public void setEditingState(boolean state) {
        isEditingState = state;
    }
}

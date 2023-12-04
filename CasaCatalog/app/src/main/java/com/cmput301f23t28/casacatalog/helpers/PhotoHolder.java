package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.views.SquaredImageView;

public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
    public SquaredImageView img_android;
    private final ImageView checkbox;
    private final ListClickListener<PhotoHolder> mListener;
    public PhotoHolder(@NonNull View photoView, ListClickListener<PhotoHolder> listener) {
        super(photoView);
        img_android = photoView.findViewById(R.id.photo);
        checkbox = photoView.findViewById(R.id.photo_checkbox);
        this.mListener = listener;

        photoView.setOnClickListener(this);
        photoView.setOnLongClickListener(this);
    }

    /**
     * Connects the click listener with the holder.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * Connects the click listener with the holder.
     * @param v The view that was clicked and held.
     *
     * @return whether the click has happened
     */
    @Override
    public boolean onLongClick(View v) {
        if (mListener != null) {
            mListener.onItemLongClick(getAdapterPosition());
            return true;
        }
        return false;
    }

    /**
     * Displays the checkbox for the photo view holder
     * @param checked where to check or not to check
     */
    public void setChecked(boolean checked) {
        if (checked) {
            checkbox.setImageResource(R.drawable.baseline_check_circle_outline_24);
        } else {
            checkbox.setImageResource(R.drawable.round_radio_button_unchecked_24);
        }

    }

    /**
     * Displays the checkbox for the photo view holder
     * @param visible whether to be visible or not
     */
    public void revealChecked(boolean visible) {
        if (visible) {
            checkbox.setVisibility(View.VISIBLE);
        } else {
            checkbox.setVisibility(View.GONE);
        }
    }
}

package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.views.SquaredImageView;

public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
    public SquaredImageView img_android;
    private final ListClickListener<PhotoHolder> mListener;
    public PhotoHolder(@NonNull View photoView, ListClickListener<PhotoHolder> listener) {
        super(photoView);
        img_android = photoView.findViewById(R.id.photo);
        this.mListener = listener;

        photoView.setOnClickListener(this);
        photoView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(getAdapterPosition(), this);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mListener != null) {
            mListener.onItemLongClick(getAdapterPosition(), this);
            return true;
        }
        return false;
    }
}
